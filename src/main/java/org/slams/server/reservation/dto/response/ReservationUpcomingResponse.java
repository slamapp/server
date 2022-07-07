package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.reservation.entity.Reservation;

import java.time.LocalDateTime;

@Getter
public class ReservationUpcomingResponse extends BaseResponse {

    private String id;
    private CourtInMapDto court;
    private Long numberOfReservations;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private ReservationUpcomingResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, CourtInMapDto court, Long numberOfReservations, LocalDateTime startTime, LocalDateTime endTime) {
        super(createdAt, updatedAt);
        this.id = id;
        this.court = court;
        this.numberOfReservations = numberOfReservations;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ReservationUpcomingResponse of(Reservation reservation, Long numberOfReservations){
        return new ReservationUpcomingResponse(reservation.getCreatedAt(), reservation.getUpdatedAt(),
            reservation.getId().toString(), CourtInMapDto.toDto(reservation.getCourt()), numberOfReservations, reservation.getStartTime(), reservation.getEndTime());
    }

}
