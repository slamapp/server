package org.slams.server.notification.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slams.server.chat.dto.response.ChatContentsResponse;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.common.api.TokenGetId;
import org.slams.server.common.error.exception.EntityNotFoundException;
import org.slams.server.notification.dto.request.FollowNotificationRequest;
import org.slams.server.notification.dto.request.LoudspeakerNotificationRequest;
import org.slams.server.notification.dto.request.UpdateIsClickedStatusRequest;
import org.slams.server.notification.dto.response.NotificationResponse;
import org.slams.server.notification.exception.NotificationNotFoundException;
import org.slams.server.notification.service.NotificationService;
import org.slams.server.notification.service.WebsocketNotificationService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yunyun on 2021/12/09.
 */

@ApiOperation("공지 도메인")
@RestController
@RequestMapping(value = "/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final WebsocketNotificationService websocketNotificationService;
    private final Jwt jwt;


    @ApiOperation("특정 사용자가 받은 모든 공지 추출")
    @ApiResponses({
            @ApiResponse(
                    code = 200
                    , message = "조회 성공"
            ),
            @ApiResponse(
                    code = 400
                    , message = "존재하지 않는 공지 정보를 요청한 경우"
                    , response = EntityNotFoundException.class
            )
    })
    @GetMapping
    public ResponseEntity<CursorPageResponse<List<NotificationResponse>>> findByUserId(
            CursorPageRequest cursorRequest,
            HttpServletRequest request){

        Long userId = new TokenGetId(request,jwt).getUserId();
        List<NotificationResponse> notificationResponseList = notificationService.findAllByUserId(userId, cursorRequest);

        return ResponseEntity.ok(new CursorPageResponse<>(
                notificationResponseList,
                notificationService.findNotificationLastId(userId, cursorRequest)
        ));
    }

    @ApiOperation("읽지 않은 상태의 공지를 읽기 상태로 변환")
    @ApiResponses({
            @ApiResponse(
                    code = 201
                    , message = "읽기(status)로 상태 변경 완료"
            )
    })
    @PutMapping("/read")
    public ResponseEntity<Void> updateIsClicked(HttpServletRequest request){
        Long userId = new TokenGetId(request,jwt).getUserId();
        notificationService.updateIsClickedStatus(new UpdateIsClickedStatusRequest(true), userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("[공지] 팔로우 공지 전송")
    @PostMapping("/follow")
    public ResponseEntity<Void> saveFollowNotification(
            String receiverId,
            HttpServletRequest request
    ){
        websocketNotificationService.saveFollowNotification(
               Long.parseLong(receiverId),
               new TokenGetId(request,jwt).getUserId()
       );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("[공지] 팔로우 취소")
    @DeleteMapping("/follow")
    public ResponseEntity<Void> deleteFollowNotification(
            String receiverId,
            HttpServletRequest request
    ){
        websocketNotificationService.deleteFollowNotification(
                Long.parseLong(receiverId),
                new TokenGetId(request,jwt).getUserId()
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("[공지] 농구장 확성기 공지 전송")
    @PostMapping("/loudspeaker")
    public ResponseEntity<Void> saveLoudSpeakerAndThenSending(
            LoudspeakerNotificationRequest message,
            HttpServletRequest request
    ){
        websocketNotificationService.saveLoudSpeakerAndThenSending(
                message,
                new TokenGetId(request,jwt).getUserId()
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
