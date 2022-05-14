package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.user.dto.response.CreatorDto;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtLookUpResponse {

	private NewCourtDto newCourt;
	private CreatorDto creator;

	private NewCourtLookUpResponse(NewCourtDto newCourt, CreatorDto creator) {
		this.newCourt = newCourt;
		this.creator = creator;
	}

	public static NewCourtLookUpResponse toResponse(NewCourt newCourt){
		return new NewCourtLookUpResponse(NewCourtDto.toDto(newCourt), CreatorDto.toDto(newCourt.getProposer()));
	}

}
