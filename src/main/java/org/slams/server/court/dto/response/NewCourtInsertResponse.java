package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtInsertResponse {

    private NewCourtDto newCourt;

    private NewCourtInsertResponse(NewCourtDto newCourt) {
        this.newCourt = newCourt;
    }

    public static NewCourtInsertResponse toResponse(NewCourt newCourt){
        return new NewCourtInsertResponse(NewCourtDto.toDto(newCourt));
    }

}
