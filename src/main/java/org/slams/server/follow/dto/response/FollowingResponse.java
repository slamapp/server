package org.slams.server.follow.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.follow.entity.Follow;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowingResponse {

	private String id;
	private FollowRecieverDto receiver;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private FollowingResponse(String id, FollowRecieverDto receiver, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.receiver = receiver;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static FollowingResponse toResponse(Follow follow) {
		return new FollowingResponse(follow.getId().toString(), FollowRecieverDto.toDto(follow.getFollowing()), follow.getCreatedAt(), follow.getUpdatedAt());
	}

}
