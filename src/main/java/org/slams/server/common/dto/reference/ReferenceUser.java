package org.slams.server.common.dto.reference;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.User;

@Getter
public class ReferenceUser extends User implements BaseReferenceDto {

    @Builder
    public ReferenceUser(
            String id,
            String nickname,
            String profileImage
    ) {
        super(id, nickname, profileImage);
    }

}
