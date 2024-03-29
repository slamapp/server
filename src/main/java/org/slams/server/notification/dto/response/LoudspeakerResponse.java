package org.slams.server.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;
import org.slams.server.notification.common.ValidationMessage;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by yunyun on 2022/01/24.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoudspeakerResponse {
    private String id;
    private User user;
    private Court court;
    private Instant startTime;

    @Builder
    public LoudspeakerResponse(
            String id,
            User user,
            Court court,
            Instant startTime
    ){
        checkArgument(id != null, ValidationMessage.NOTNULL_ID);
        checkArgument(user != null, ValidationMessage.NOTNULL_USER);
        checkArgument(court != null, ValidationMessage.NOTNULL_COURT);
        checkArgument(startTime != null, ValidationMessage.NOTNULL_START_TIME);

        this.id = id;
        this.user = user;
        this.court = court;
        this.startTime = startTime;
    }


}
