package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyProfileUpdateResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private MyProfileUpdateResponse(String id, String nickname, String profileImage,
								   String description, Proficiency proficiency, List<Position> positions,
								   LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.proficiency = proficiency;
		this.positions = positions;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static MyProfileUpdateResponse of(User user) {
		return new MyProfileUpdateResponse(user.getId().toString(), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getProficiency(), user.getPositions(),
			user.getCreatedAt(), user.getUpdatedAt());
	}

}
