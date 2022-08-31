package org.slams.server.common.error.exception;

public interface CustomException {

    ErrorCode errorCode = null;

    public ErrorCode getErrorCode();
}
