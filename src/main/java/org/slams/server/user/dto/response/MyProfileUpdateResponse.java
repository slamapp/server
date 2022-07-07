package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.User;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyProfileUpdateResponse {

	private DefaultUserDto user;

	public MyProfileUpdateResponse(DefaultUserDto user) {
		this.user = user;
	}

	public static MyProfileUpdateResponse toResponse(User user) {
		return new MyProfileUpdateResponse(DefaultUserDto.toDto(user));
	}

}
