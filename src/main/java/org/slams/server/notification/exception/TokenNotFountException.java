package org.slams.server.notification.exception;

import com.amazonaws.services.kms.model.NotFoundException;

/**
 * Created by yunyun on 2021/12/16.
 */
public class TokenNotFountException extends NotFoundException {
    public TokenNotFountException(String message) {
        super(message);
    }
}
