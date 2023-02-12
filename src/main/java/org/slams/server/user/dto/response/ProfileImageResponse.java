package org.slams.server.user.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ProfileImageResponse extends BaseResponse {

	private String id;
	private String nickname;
	private String profileImage;

	private ProfileImageResponse(Instant createdAt, Instant updatedAt, String id, String nickname, String profileImage) {
		super(createdAt, updatedAt);
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}

	public static ProfileImageResponse of(User user){
		return new ProfileImageResponse(user.getCreatedAt().toInstant(ZoneOffset.UTC),
			user.getUpdatedAt().toInstant(ZoneOffset.UTC), user.getId().toString(), user.getNickname(), user.getProfileImage());
	}

}
