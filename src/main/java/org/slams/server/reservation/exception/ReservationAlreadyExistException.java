package org.slams.server.reservation.exception;

import org.slams.server.common.error.exception.ErrorCode;
import org.slams.server.common.error.exception.InvalidValueException;

public class ReservationAlreadyExistException extends InvalidValueException {

	public ReservationAlreadyExistException(String value) {
		super(value, ErrorCode.RESERVATION_ALREADY_EXIST);
	}

}
