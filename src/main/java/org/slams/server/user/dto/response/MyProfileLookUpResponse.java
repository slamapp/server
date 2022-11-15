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
public class MyProfileLookUpResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Role role;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();
	private Long followerCount;
	private Long followingCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private MyProfileLookUpResponse(String id, String nickname, String profileImage,
								   String description, Role role, Proficiency proficiency, List<Position> positions,
								   Long followerCount, Long followingCount,
								   LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.role = role;
		this.proficiency = proficiency;
		this.positions = positions;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static MyProfileLookUpResponse toResponse(User user, Long followerCount, Long followingCount) {
		return new MyProfileLookUpResponse(String.valueOf(user.getId()), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getRole(), user.getProficiency(), user.getPositions(), followerCount, followingCount,
			user.getCreatedAt(), user.getUpdatedAt());
	}

}
