package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BriefUserInfoDto {

	private String id;
	private String nickname;
	private String profileImage;

	private BriefUserInfoDto(String id, String nickname, String profileImage) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}

	public static BriefUserInfoDto toDto(User user){
		return new BriefUserInfoDto(user.getId().toString(), user.getNickname(), user.getProfileImage());
	}

}
