package org.slams.server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.BriefCourtInfoDto;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Getter
public class UserProfileLookUpResponse extends BaseResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Proficiency proficiency;
	private List<Position> positions;
	private Boolean isFollowing;
	private Long followerCount;
	private Long followingCount;
	private List<BriefCourtInfoDto> favoriteCourts;

	private UserProfileLookUpResponse(Instant createdAt, Instant updatedAt,
									  String id, String nickname, String profileImage, String description,
									  Proficiency proficiency, List<Position> positions,
									  Boolean isFollowing, Long followerCount, Long followingCount, List<BriefCourtInfoDto> favoriteCourts) {
		super(createdAt, updatedAt);
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.proficiency = proficiency;
		this.positions = positions;
		this.isFollowing = isFollowing;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.favoriteCourts = favoriteCourts;
	}

	public static UserProfileLookUpResponse toResponse(User user, Boolean isFollowing, Long followerCount, Long followingCount, List<BriefCourtInfoDto> favoriteCourts) {
		return new UserProfileLookUpResponse(user.getCreatedAt().toInstant(ZoneOffset.UTC), user.getUpdatedAt().toInstant(ZoneOffset.UTC),
			user.getId().toString(), user.getNickname(), user.getProfileImage(), user.getDescription(),
			user.getProficiency(), user.getPositions(), isFollowing, followerCount, followingCount, favoriteCourts
		);
	}

}
