package org.slams.server.common.dto.referenceDto;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.apiTest.Reference;
import org.slams.server.common.dto.Loudspeaker;
import org.slams.server.common.dto.User;

@Getter
public class UserAndLoudspeakerResponse {
    private final Reference<User> user;
    private final Reference<Loudspeaker> loudspeaker;

    @Builder
    public UserAndLoudspeakerResponse(
            Reference<User> user,
            Reference<Loudspeaker> loudspeaker
    ) {
        this.user= user;
        this.loudspeaker = loudspeaker;
    }

}
