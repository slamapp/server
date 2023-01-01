package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.reservation.entity.Reservation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ReservationExpiredResponse extends BaseResponse {

	private String id;
	private CourtInMapDto court;
	private Instant startTime;
	private Instant endTime;
	private Long numberOfReservations;

	private ReservationExpiredResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, CourtInMapDto court, Instant startTime, Instant endTime, Long numberOfReservations) {
		super(createdAt, updatedAt);
		this.id = id;
		this.court = court;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numberOfReservations = numberOfReservations;
	}

	public static ReservationExpiredResponse of(Reservation reservation, Long numberofReservations) {
		return new ReservationExpiredResponse(reservation.getCreatedAt(), reservation.getUpdatedAt(),
			reservation.getId().toString(), CourtInMapDto.toDto(reservation.getCourt()), reservation.getStartTime().toInstant(ZoneOffset.UTC), reservation.getEndTime().toInstant(ZoneOffset.UTC), numberofReservations);
	}

}
