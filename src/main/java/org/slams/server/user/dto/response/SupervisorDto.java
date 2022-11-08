package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupervisorDto {

	private String id;
	private String nickname;

	private SupervisorDto(String id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public static SupervisorDto toDto(User user){
		return new SupervisorDto(user.getId().toString(), user.getNickname());
	}

}
