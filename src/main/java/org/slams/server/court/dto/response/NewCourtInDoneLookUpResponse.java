package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.user.dto.response.CreatorDto;
import org.slams.server.user.dto.response.SupervisorDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtInDoneLookUpResponse {

	private NewCourtDto newCourt;
	private CreatorDto creator;
	private SupervisorDto supervisor;

	private NewCourtInDoneLookUpResponse(NewCourtDto newCourt, CreatorDto creator, SupervisorDto supervisor) {
		this.newCourt = newCourt;
		this.creator = creator;
		this.supervisor = supervisor;
	}

	public static NewCourtInDoneLookUpResponse toResponse(NewCourt newCourt) {
		return new NewCourtInDoneLookUpResponse(
			NewCourtDto.toDto(newCourt), CreatorDto.toDto(newCourt.getProposer()), SupervisorDto.toDto(newCourt.getSupervisor())
		);
	}

}
