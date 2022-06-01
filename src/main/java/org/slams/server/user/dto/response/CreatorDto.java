package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatorDto {

	private String id;
	private String nickname;
	private String profileImage;
	private Role role;

	private CreatorDto(String id, String nickname, String profileImage, Role role) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.role = role;
	}

	public static CreatorDto toDto(User user){
		return new CreatorDto(user.getId().toString(), user.getNickname(), user.getProfileImage(), user.getRole());
	}

}
