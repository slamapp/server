package org.slams.server.reservation.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ReservationInsertResponse extends BaseResponse {

	private String id;
	private String courtId;
	private String userId;
	private Instant startTime;
	private Instant endTime;
	private boolean hasBall;

	private ReservationInsertResponse(Instant createdAt, Instant updatedAt, String id, String courtId, String userId, Instant startTime, Instant endTime, boolean hasBall) {
		super(createdAt, updatedAt);
		this.id = id;
		this.courtId = courtId;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
	}

	public static ReservationInsertResponse of(Reservation reservation) {
		return new ReservationInsertResponse(reservation.getCreatedAt().toInstant(ZoneOffset.UTC), reservation.getUpdatedAt().toInstant(ZoneOffset.UTC),
			reservation.getId().toString(), reservation.getCourt().getId().toString(), reservation.getUser().getId().toString(),
			reservation.getStartTime().toInstant(ZoneOffset.UTC), reservation.getEndTime().toInstant(ZoneOffset.UTC), reservation.isHasBall());
	}

}
