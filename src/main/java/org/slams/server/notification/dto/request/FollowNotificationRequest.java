package org.slams.server.notification.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yunyun on 2021/12/14.
 */

@Getter
public class FollowNotificationRequest {
    private Long receiverId;

    @Builder
    public FollowNotificationRequest(Long receiverId) {
        this.receiverId = receiverId;
    }
}
