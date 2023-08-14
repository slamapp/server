package org.slams.server.follow.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.follow.entity.Follow;

import java.time.Instant;
import java.time.ZoneOffset;

@Getter
public class FollowingResponse extends BaseResponse {

	private String id;
	private FollowRecieverDto receiver;

	private FollowingResponse(Instant createdAt, Instant updatedAt, String id, FollowRecieverDto receiver) {
		super(createdAt, updatedAt);
		this.id = id;
		this.receiver = receiver;
	}

	public static FollowingResponse toResponse(Follow follow) {
		return new FollowingResponse(follow.getCreatedAt().toInstant(ZoneOffset.UTC), follow.getUpdatedAt().toInstant(ZoneOffset.UTC),
			follow.getId().toString(), FollowRecieverDto.toDto(follow.getFollowing()));
	}

}
