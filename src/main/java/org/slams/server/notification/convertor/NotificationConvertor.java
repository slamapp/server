package org.slams.server.notification.convertor;

import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.Follow;
import org.slams.server.common.dto.User;
import org.slams.server.common.error.exception.ErrorCode;
import org.slams.server.notification.exception.InvalidNotificationTypeException;
import org.slams.server.notification.dto.response.LoudspeakerResponse;
import org.slams.server.notification.dto.response.NotificationResponse;
import org.slams.server.notification.entity.Notification;
import org.slams.server.notification.entity.NotificationType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yunyun on 2021/12/12.
 */

@Component
public class NotificationConvertor {

    public List<NotificationResponse> toDtoList(List<Notification> notifications){

        if (notifications.isEmpty()){
            return Collections.emptyList();
        }

        return notifications.stream()
                .map(v -> toDto(v))
                .collect(Collectors.toList());
    }

    public NotificationResponse toDto(Notification notification){
        if (notification.getType().equals(NotificationType.LOUDSPEAKER)){
            return NotificationResponse.createForLoudspeakerNotification(
                    notification.getId(),
                    NotificationType.LOUDSPEAKER,
                    LoudspeakerResponse.builder()
                            .court(Court.builder()
                                    .id(notification.getLoudSpeaker().getCourt().getId().toString())
                                    .name(notification.getLoudSpeaker().getCourt().getName())
                                    .texture(notification.getLoudSpeaker().getCourt().getTexture())
                                    .longitude(notification.getLoudSpeaker().getCourt().getLongitude())
                                    .latitude(notification.getLoudSpeaker().getCourt().getLatitude())
                                    .image(notification.getLoudSpeaker().getCourt().getImage())
                                    .basketCount(notification.getLoudSpeaker().getCourt().getBasketCount())
                                    .build())
                            .startTime(notification.getLoudSpeaker().getStartTime())
                            .build(),
                    notification.isRead(),
                    notification.isClicked(),
                    notification.getCreatedAt(),
                    notification.getUpdatedAt()
            );
        }

        if (notification.getType().equals(NotificationType.FOLLOW)){
            return NotificationResponse.createForFollowNotification(
                    notification.getId(),
                    NotificationType.FOLLOW,
                    Follow.builder()
                            .sender(
                                    User.builder()
                                            .id(notification.getFollow().getFollower().getId().toString())
                                            .profileImage(notification.getFollow().getFollower().getProfileImage())
                                            .nickname(notification.getFollow().getFollower().getNickname())
                                            .build()
                            )
                            .build(),
                    notification.isRead(),
                    notification.isClicked(),
                    notification.getCreatedAt(),
                    notification.getUpdatedAt()
            );
        }

        throw new InvalidNotificationTypeException(ErrorCode.INVALID_NOTIFICATION_TYPE);
    }


}
