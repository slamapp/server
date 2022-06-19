package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.court.entity.Court;

@Getter
public class CourtByDateAndBoundaryResponse {

	private CourtInMapDto court;
	private Long reservationMaxCount;

	private CourtByDateAndBoundaryResponse(CourtInMapDto court, Long reservationMaxCount) {
		this.court = court;
		this.reservationMaxCount = reservationMaxCount;
	}

	public static CourtByDateAndBoundaryResponse toResponse(Court court, Long reservationMaxCount) {
		return new CourtByDateAndBoundaryResponse(CourtInMapDto.toDto(court), reservationMaxCount);
	}

}
