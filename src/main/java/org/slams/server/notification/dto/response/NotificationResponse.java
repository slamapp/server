package org.slams.server.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.slams.server.common.dto.Follow;
import org.slams.server.notification.entity.NotificationType;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by yunyun on 2021/12/08.
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    private final String id;

    private final NotificationType type;

    private final Follow follow;

    private final LoudspeakerResponse loudspeaker;

    private final Boolean isRead;

    private final Boolean isClicked;

    private final Instant createdAt;
    private final Instant updatedAt;


    private NotificationResponse(
            String id,
            NotificationType type,
            Follow follow,
            LoudspeakerResponse loudspeaker,
            boolean isRead,
            boolean isClicked,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = id;
        this.type = type;
        this.follow = follow;
        this.loudspeaker = loudspeaker;
        this.isRead = isRead;
        this.isClicked = isClicked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static NotificationResponse createForFollowNotification(
            String id,
            NotificationType type,
            Follow follow,
            boolean isRead,
            boolean isClicked,
            Instant createdAt,
            Instant updatedAt
    ){
        return new NotificationResponse(id, type, follow, null, isRead, isClicked, createdAt, updatedAt);
    }

    public static NotificationResponse createForLoudspeakerNotification(
            String id,
            NotificationType type,
            LoudspeakerResponse loudspeaker,
            boolean isRead,
            boolean isClicked,
            Instant createdAt,
            Instant updatedAt
    ){
        return new NotificationResponse(id, type, null, loudspeaker, isRead, isClicked, createdAt, updatedAt);
    }

}
