package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.reservation.entity.Reservation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ReservationUpcomingResponse extends BaseResponse {

    private String id;
    private CourtInMapDto court;
    private Long numberOfReservations;
    private Instant startTime;
    private Instant endTime;

    private ReservationUpcomingResponse(Instant createdAt, Instant updatedAt, String id, CourtInMapDto court, Long numberOfReservations, Instant startTime, Instant endTime) {
        super(createdAt, updatedAt);
        this.id = id;
        this.court = court;
        this.numberOfReservations = numberOfReservations;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ReservationUpcomingResponse of(Reservation reservation, Long numberOfReservations){
        return new ReservationUpcomingResponse(reservation.getCreatedAt().toInstant(ZoneOffset.UTC), reservation.getUpdatedAt().toInstant(ZoneOffset.UTC),
            reservation.getId().toString(), CourtInMapDto.toDto(reservation.getCourt()), numberOfReservations, reservation.getStartTime().toInstant(ZoneOffset.UTC), reservation.getEndTime().toInstant(ZoneOffset.UTC));
    }

}
