package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.dto.response.BriefCourtInfoDto;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileLookUpResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Proficiency proficiency;
	private List<Position> positions;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean isFollowing;
	private Long followerCount;
	private Long followingCount;
	private List<BriefCourtInfoDto> favoriteCourts;

	private UserProfileLookUpResponse(String id, String nickname, String profileImage, String description,
									  Proficiency proficiency, List<Position> positions,
									  LocalDateTime createdAt, LocalDateTime updatedAt,
									  Boolean isFollowing, Long followerCount, Long followingCount, List<BriefCourtInfoDto> favoriteCourts) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.proficiency = proficiency;
		this.positions = positions;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isFollowing = isFollowing;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.favoriteCourts = favoriteCourts;
	}

	public static UserProfileLookUpResponse toResponse(User user, Boolean isFollowing, Long followerCount, Long followingCount, List<BriefCourtInfoDto> favoriteCourts) {
		return new UserProfileLookUpResponse(user.getId().toString(), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getProficiency(), user.getPositions(),
			user.getCreatedAt(), user.getUpdatedAt(), isFollowing, followerCount, followingCount, favoriteCourts
		);
	}

}
