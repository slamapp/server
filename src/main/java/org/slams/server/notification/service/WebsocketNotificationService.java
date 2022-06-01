package org.slams.server.notification.service;

import org.slams.server.chat.dto.response.ChatContentsResponse;
import org.slams.server.chat.service.ChatContentsService;
import org.slams.server.follow.service.FollowService;
import org.slams.server.notification.dto.request.FollowNotificationRequest;
import org.slams.server.notification.dto.request.LoudspeakerNotificationRequest;
import org.slams.server.notification.dto.response.NotificationResponse;
import org.slams.server.reservation.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yunyun on 2022/06/01.
 */
@Service
public class WebsocketNotificationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NotificationService notificationService;
    private final SimpMessagingTemplate websocket;
    private final ReservationRepository reservationRepository;
    private final FollowService followService;
    private final ChatContentsService chatContentsService;

    public WebsocketNotificationService(
            SimpMessagingTemplate websocket,
            NotificationService notificationService,
            ReservationRepository reservationRepository,
            FollowService followService,
            ChatContentsService chatContentsService){
        this.websocket = websocket;
        this.notificationService = notificationService;
        this.reservationRepository = reservationRepository;
        this.followService = followService;
        this.chatContentsService = chatContentsService;
    }

    public void saveFollowNotification(
            Long receiverId,
            Long userId
    ){
        if(userId.equals(receiverId)){
            throw new RuntimeException("자기 자신을 팔로우 할 수 없습니다.");
        }

        followService.follow(userId, receiverId);
        NotificationResponse notification = notificationService.saveForFollowNotification(receiverId, userId);

        websocket.convertAndSend(
                String.format("/user/%s/notification", receiverId),
                notification
        );
    }

    public void deleteFollowNotification(
            Long receiverId,
            Long userId
    ){

        notificationService.deleteFollowNotification(receiverId, userId);
        followService.unfollow(userId, receiverId);
    }
    public void saveLoudSpeakerAndThenSending(
            LoudspeakerNotificationRequest message,
            Long senderId
    ){
        List<Long> receiverIds = reservationRepository.findBeakerIdByCourtId(Long.valueOf(message.getCourtId()));

        for (Long receiverId : receiverIds){
            if (receiverId.equals(senderId)){
                continue;
            }
            NotificationResponse notification = notificationService.saveForLoudSpeakerNotification(message, receiverId, senderId);
            websocket.convertAndSend(
                    String.format("/user/%s/notification", receiverId),
                    notification
            );
        }

        ChatContentsResponse chatContentsResponse = chatContentsService.saveChatLoudSpeakerContent(message, senderId);
        websocket.convertAndSend(
                String.format("/user/%s/chat", message.getCourtId()),
                chatContentsResponse
        );
    }
}
