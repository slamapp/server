package org.slams.server.notification.exception;

import org.slams.server.common.error.exception.BusinessException;
import org.slams.server.common.error.exception.ErrorCode;

public class NotificationNotFoundException extends BusinessException {
    public NotificationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    public NotificationNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
