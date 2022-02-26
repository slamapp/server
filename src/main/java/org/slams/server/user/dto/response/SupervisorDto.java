package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupervisorDto {

	private String id;
	private String nickname;
	private String profileImage;

	private SupervisorDto(String id, String nickname, String profileImage) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}

	public static SupervisorDto toDto(User supervisor){
		return new SupervisorDto(supervisor.getId().toString(), supervisor.getNickname(), supervisor.getProfileImage());
	}
}
