package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ReservationUpdateResponse extends BaseResponse {

	private String id;
	private Instant startTime;
	private Instant endTime;
	private Boolean hasBall;

	private ReservationUpdateResponse(Instant createdAt, Instant updatedAt, String id, Instant startTime, Instant endTime, Boolean hasBall) {
		super(createdAt, updatedAt);
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
	}

	public static ReservationUpdateResponse of(Reservation reservation) {
		return new ReservationUpdateResponse(reservation.getCreatedAt().toInstant(ZoneOffset.UTC), reservation.getUpdatedAt().toInstant(ZoneOffset.UTC),
			reservation.getId().toString(), reservation.getStartTime().toInstant(ZoneOffset.UTC), reservation.getEndTime().toInstant(ZoneOffset.UTC), reservation.isHasBall());
	}

}
