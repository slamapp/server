package org.slams.server.follow.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.follow.entity.Follow;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class FollowerResponse extends BaseResponse {

	private String id;
	private FollowCreatorDto creator;

	private FollowerResponse(Instant createdAt, Instant updatedAt, String id, FollowCreatorDto creator) {
		super(createdAt, updatedAt);
		this.id = id;
		this.creator = creator;
	}

	public static FollowerResponse toResponse(Follow follow) {
		return new FollowerResponse(follow.getCreatedAt().toInstant(ZoneOffset.UTC), follow.getUpdatedAt().toInstant(ZoneOffset.UTC),
			follow.getId().toString(), FollowCreatorDto.toDto(follow.getFollower()));
	}

}
