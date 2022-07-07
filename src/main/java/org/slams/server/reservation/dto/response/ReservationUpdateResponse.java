package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;

import java.time.LocalDateTime;

@Getter
public class ReservationUpdateResponse extends BaseResponse {

	private String id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Boolean hasBall;

	private ReservationUpdateResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, LocalDateTime startTime, LocalDateTime endTime, Boolean hasBall) {
		super(createdAt, updatedAt);
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
	}

	public static ReservationUpdateResponse of(Reservation reservation) {
		return new ReservationUpdateResponse(reservation.getCreatedAt(), reservation.getUpdatedAt(),
			reservation.getId().toString(), reservation.getStartTime(), reservation.getEndTime(), reservation.isHasBall());
	}

}
