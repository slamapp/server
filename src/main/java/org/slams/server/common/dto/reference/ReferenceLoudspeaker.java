package org.slams.server.common.dto.reference;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.Loudspeaker;
import org.slams.server.common.dto.User;

import java.time.LocalDateTime;

@Getter
public class ReferenceLoudspeaker extends Loudspeaker implements BaseReferenceDto {

    @Builder
    public ReferenceLoudspeaker(
            String id,
            User creator,
            Court court,
            LocalDateTime startTime
    ) {
        super(id, creator, court, startTime);
    }
}
