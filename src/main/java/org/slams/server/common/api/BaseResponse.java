package org.slams.server.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class BaseResponse {

	private Instant createdAt;
	private Instant updatedAt;

}

