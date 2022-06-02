package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.user.dto.response.CreatorDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtInReadyLookUpResponse {

	private NewCourtDto newCourt;
	private CreatorDto creator;

	private NewCourtInReadyLookUpResponse(NewCourtDto newCourt, CreatorDto creator) {
		this.newCourt = newCourt;
		this.creator = creator;
	}

	public static NewCourtInReadyLookUpResponse toResponse(NewCourt newCourt) {
		return new NewCourtInReadyLookUpResponse(NewCourtDto.toDto(newCourt), CreatorDto.toDto(newCourt.getProposer()));
	}

}
