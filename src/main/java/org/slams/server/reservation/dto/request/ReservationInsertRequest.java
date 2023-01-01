package org.slams.server.reservation.dto.request;

import lombok.*;
import org.slams.server.court.entity.Court;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.user.entity.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationInsertRequest {

	@NotNull(message = "코트 id는 필수 값입니다.")
	private Long courtId;

	@Future(message = "예약은 현재보다 미래만 가능합니다.")
	private Instant startTime;

	@Future(message = "예약은 현재보다 미래만 가능합니다.")
	private Instant endTime;

	@NotNull(message = "농구공 유무는 필수 값입니다.")
	private Boolean hasBall;

	@Builder
	public ReservationInsertRequest(Long courtId, Instant startTime, Instant endTime, Boolean hasBall) {
		this.courtId = courtId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hasBall = hasBall;
	}

	public Reservation toEntity(ReservationInsertRequest request, Court court, User user) {
		return Reservation.builder()
			.court(court)
			.user(user)
			.startTime(request.getStartTime().atOffset(ZoneOffset.UTC).toLocalDateTime())
			.endTime(request.getEndTime().atOffset(ZoneOffset.UTC).toLocalDateTime())
			.hasBall(request.getHasBall())
			.build();
	}

}
