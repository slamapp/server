package org.slams.server.user.exception;

import org.slams.server.common.error.exception.BusinessException;
import org.slams.server.common.error.exception.ErrorCode;

public class UserNotAuthorizedException extends BusinessException {

	public UserNotAuthorizedException() {
		super(ErrorCode.USER_NOT_AUTHORIZED);
	}

}
