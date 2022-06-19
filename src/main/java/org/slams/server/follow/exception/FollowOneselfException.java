package org.slams.server.follow.exception;

import org.slams.server.common.error.exception.ErrorCode;
import org.slams.server.common.error.exception.InvalidValueException;

public class FollowOneselfException extends InvalidValueException {

	public FollowOneselfException(String value) {
		super(value, ErrorCode.DONT_FOLLOW_ONESELF);
	}

}
