package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.reservation.entity.Reservation;

import java.time.LocalDateTime;

@Getter
public class ReservationUpdateResponseDto extends BaseResponse {


    private Long reservationId;
    private Long courtId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean hasBall;

    public ReservationUpdateResponseDto(Reservation reservation) {
        super(reservation.getCreatedAt(), reservation.getUpdatedAt());
        reservationId=reservation.getId();
        courtId=reservation.getCourt().getId();
        startTime=reservation.getStartTime();
        endTime=reservation.getEndTime();
        hasBall=reservation.isHasBall();
    }


}
