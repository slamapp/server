package org.slams.server.common.dto.referenceDto;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.apiTest.Reference;
import org.slams.server.common.dto.User;

@Getter
public class UserResponse {
    private final Reference<User> user;

    @Builder
    public UserResponse(Reference<User> user) {
        this.user= user;
    }
}
