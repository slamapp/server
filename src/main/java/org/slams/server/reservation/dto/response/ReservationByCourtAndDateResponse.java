package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.user.dto.response.BriefUserInfoDto;

import java.time.LocalDateTime;

@Getter
public class ReservationByCourtAndDateResponse extends BaseResponse {

	private String id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean hasBall;
	private BriefUserInfoDto user;

	private ReservationByCourtAndDateResponse(LocalDateTime createdAt, LocalDateTime updatedAt,
											  String id, LocalDateTime startTime, LocalDateTime endTime, boolean hasBall, BriefUserInfoDto user) {
		super(createdAt, updatedAt);
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
		this.user = user;
	}

	public static ReservationByCourtAndDateResponse of(Reservation reservation) {
		return new ReservationByCourtAndDateResponse(reservation.getCreatedAt(), reservation.getUpdatedAt(),
			reservation.getId().toString(), reservation.getStartTime(), reservation.getEndTime(), reservation.isHasBall(), BriefUserInfoDto.toDto(reservation.getUser()));
	}

}
