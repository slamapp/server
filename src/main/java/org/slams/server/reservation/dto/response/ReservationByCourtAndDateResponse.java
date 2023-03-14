package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.user.dto.response.BriefUserInfoDto;

import java.time.Instant;
import java.time.ZoneOffset;

@Getter
public class ReservationByCourtAndDateResponse extends BaseResponse {

	private String id;
	private Instant startTime;
	private Instant endTime;
	private boolean hasBall;
	private BriefUserInfoDto user;

	private ReservationByCourtAndDateResponse(Instant createdAt, Instant updatedAt,
											  String id, Instant startTime, Instant endTime, boolean hasBall, BriefUserInfoDto user) {
		super(createdAt, updatedAt);
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
		this.user = user;
	}

	public static ReservationByCourtAndDateResponse of(Reservation reservation) {
		return new ReservationByCourtAndDateResponse(reservation.getCreatedAt().toInstant(ZoneOffset.UTC), reservation.getUpdatedAt().toInstant(ZoneOffset.UTC),
			reservation.getId().toString(), reservation.getStartTime().toInstant(ZoneOffset.UTC), reservation.getEndTime().toInstant(ZoneOffset.UTC), reservation.isHasBall(), BriefUserInfoDto.toDto(reservation.getUser()));
	}

}
