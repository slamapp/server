package org.slams.server.notification.exception;

import org.slams.server.common.error.exception.BusinessException;
import org.slams.server.common.error.exception.ErrorCode;
import org.slams.server.common.error.exception.InvalidValueException;

public class InvalidNotificationTypeException extends InvalidValueException {
    public InvalidNotificationTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
