package org.slams.server.notification.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yunyun on 2021/12/14.
 */

@Getter
public class FollowNotificationRequest {
    private String receiverId;

    public FollowNotificationRequest() {}

    @Builder
    public FollowNotificationRequest(String receiverId) {
        this.receiverId = receiverId;
    }
}
