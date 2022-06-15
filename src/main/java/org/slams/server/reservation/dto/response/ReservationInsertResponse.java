package org.slams.server.reservation.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;

import java.time.LocalDateTime;

@Getter
public class ReservationInsertResponse extends BaseResponse {

	private String id;
	private String courtId;
	private String userId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean hasBall;

	private ReservationInsertResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, String courtId, String userId, LocalDateTime startTime, LocalDateTime endTime, boolean hasBall) {
		super(createdAt, updatedAt);
		this.id = id;
		this.courtId = courtId;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
	}

	public static ReservationInsertResponse of(Reservation reservation){
		return new ReservationInsertResponse(reservation.getCreatedAt(), reservation.getUpdateAt(),
			reservation.getId().toString(), reservation.getCourt().getId().toString(), reservation.getUser().getId().toString(),
			reservation.getStartTime(), reservation.getEndTime(), reservation.isHasBall());
	}

}
