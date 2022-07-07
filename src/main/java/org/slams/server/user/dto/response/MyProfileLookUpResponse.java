package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.User;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyProfileLookUpResponse {

	private DefaultUserDto user;
	private Long followerCount;
	private Long followingCount;

	private MyProfileLookUpResponse(DefaultUserDto user, Long followerCount, Long followingCount) {
		this.user = user;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
	}

	public static MyProfileLookUpResponse toResponse(User user, Long followerCount, Long followingCount) {
		return new MyProfileLookUpResponse(DefaultUserDto.toDto(user), followerCount, followingCount);
	}

}
