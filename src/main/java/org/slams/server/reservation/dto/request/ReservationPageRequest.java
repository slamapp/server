package org.slams.server.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class ReservationPageRequest {

	private int size;
	private Instant lastStartTime;
	private Boolean isFirst;

}
