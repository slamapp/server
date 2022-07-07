package org.slams.server.reservation.dto.request;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReservationUpdateRequest {

    @NotNull(message = "예약 id는 필수 값입니다.")
    private Long reservationId;

    @Future(message = "예약은 현재보다 미래만 가능합니다.")
    private LocalDateTime startTime;

    @Future(message = "예약은 현재보다 미래만 가능합니다.")
    private LocalDateTime endTime;

    @NotNull(message = "농구공 유무는 필수 값입니다.")
    private Boolean hasBall;

}
