package org.slams.server.favorite.exception;

import org.slams.server.common.error.exception.EntityNotFoundException;
import org.slams.server.common.error.exception.ErrorCode;

public class FavoriteNotFoundException extends EntityNotFoundException {

	public FavoriteNotFoundException(String message) {
		super(message, ErrorCode.FAVORITE_NOT_FOUND);
	}

}
