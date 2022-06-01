package org.slams.server.notification.controller;

import io.swagger.annotations.ApiOperation;
import org.slams.server.chat.dto.response.ChatContentsResponse;
import org.slams.server.chat.service.ChatContentsService;
import org.slams.server.common.utils.WebsocketUtil;
import org.slams.server.follow.service.FollowService;
import org.slams.server.notification.dto.WebSocketTestDto;
import org.slams.server.notification.dto.request.FollowNotificationRequest;
import org.slams.server.notification.dto.request.LoudspeakerNotificationRequest;
import org.slams.server.notification.dto.response.NotificationResponse;
import org.slams.server.notification.service.NotificationService;
import org.slams.server.reservation.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by yunyun on 2021/12/15.
 */

@Controller
@ApiOperation("(삭제 예정) 공지 도메인 - 웹소켓")
public class NotificationWebSocketController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NotificationService notificationService;
    private final SimpMessagingTemplate websocket;
    private final ReservationRepository reservationRepository;
    private final FollowService followService;
    private final WebsocketUtil websocketUtil;
    private final ChatContentsService chatContentsService;

    public NotificationWebSocketController(
            SimpMessagingTemplate websocket,
            NotificationService notificationService,
            ReservationRepository reservationRepository,
            FollowService followService,
            WebsocketUtil websocketUtil,
            ChatContentsService chatContentsService){
        this.websocket = websocket;
        this.notificationService = notificationService;
        this.reservationRepository = reservationRepository;
        this.followService = followService;
        this.websocketUtil = websocketUtil;
        this.chatContentsService = chatContentsService;
    }

    @MessageMapping("/object")
    public void objectTest(WebSocketTestDto message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Long testUserId = websocketUtil.findTokenFromHeader(headerAccessor);

        // token 유효성 검사
        WebSocketTestDto userRequest = new WebSocketTestDto();
        userRequest.setUserId(message.getUserId());
        websocket.convertAndSend(
                "/topic/"+message.getUserId(),
                userRequest
        );
    }

    @ApiOperation("[삭제예정] 팔로우 공지 전송")
    @MessageMapping("/follow")
    public void saveFollowNotificationWebsocket(
            FollowNotificationRequest message,
            SimpMessageHeaderAccessor headerAccessor
            ){
        Long userId = websocketUtil.findTokenFromHeader(headerAccessor);

        if(userId.equals(message.getReceiverId())){
            throw new RuntimeException("자기 자신을 팔로우 할 수 없습니다.");
        }

        followService.follow(userId, Long.valueOf(message.getReceiverId()));
        NotificationResponse notification = notificationService.saveForFollowNotification(Long.parseLong(message.getReceiverId()), userId);

        websocket.convertAndSend(
                String.format("/user/%s/notification", message.getReceiverId()),
                notification
                );
    }

    @ApiOperation("[삭제예정] 팔로우 취소")
    @MessageMapping("/followcancel")
    public void deleteFollowNotificationWebsocket(
            FollowNotificationRequest message,
            SimpMessageHeaderAccessor headerAccessor
    ){
        Long userId = websocketUtil.findTokenFromHeader(headerAccessor);

        notificationService.deleteFollowNotification(Long.parseLong(message.getReceiverId()), userId);
        followService.unfollow(userId, Long.valueOf(message.getReceiverId()));
    }

    @ApiOperation("[삭제예정] 농구장 확성기 공지 전송")
    @MessageMapping("/loudspeaker")
    public void saveLoudSpeakerAndThenSendingWebsocket(
            LoudspeakerNotificationRequest request,
            SimpMessageHeaderAccessor headerAccessor
    ){
        Long sendId = websocketUtil.findTokenFromHeader(headerAccessor);
        List<Long> receiverIds = reservationRepository.findBeakerIdByCourtId(Long.valueOf(request.getCourtId()));

        for (Long receiverId : receiverIds){
            if (receiverId.equals(sendId)){
                continue;
            }
            NotificationResponse notification = notificationService.saveForLoudSpeakerNotification(request, receiverId, sendId);
            websocket.convertAndSend(
                    String.format("/user/%s/notification", receiverId),
                    notification
            );
        }

        ChatContentsResponse chatContentsResponse = chatContentsService.saveChatLoudSpeakerContent(request, sendId);
        websocket.convertAndSend(
                String.format("/user/%s/chat", request.getCourtId()),
                chatContentsResponse
        );
    }


}
