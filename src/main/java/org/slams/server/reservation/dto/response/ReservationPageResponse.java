package org.slams.server.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.formula.functions.T;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class ReservationPageResponse<T> {

	private T contents;
	private Instant lastStartTime;

}
