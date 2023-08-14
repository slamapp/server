package org.slams.server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MyProfileLookUpResponse extends BaseResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Role role;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();
	private Long followerCount;
	private Long followingCount;

	private MyProfileLookUpResponse(Instant createdAt, Instant updatedAt,
									String id, String nickname, String profileImage,
									String description, Role role, Proficiency proficiency, List<Position> positions,
									Long followerCount, Long followingCount) {
		super(createdAt, updatedAt);
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.role = role;
		this.proficiency = proficiency;
		this.positions = positions;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
	}

	public static MyProfileLookUpResponse toResponse(User user, Long followerCount, Long followingCount) {
		return new MyProfileLookUpResponse(user.getCreatedAt().toInstant(ZoneOffset.UTC), user.getUpdatedAt().toInstant(ZoneOffset.UTC),
			String.valueOf(user.getId()), user.getNickname(), user.getProfileImage(), user.getDescription(),
			user.getRole(), user.getProficiency(), user.getPositions(), followerCount, followingCount);
	}

}
