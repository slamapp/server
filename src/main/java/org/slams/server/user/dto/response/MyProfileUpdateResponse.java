package org.slams.server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MyProfileUpdateResponse extends BaseResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();

	private MyProfileUpdateResponse(Instant createdAt, Instant updatedAt,
									String id, String nickname, String profileImage,
									String description, Proficiency proficiency, List<Position> positions) {
		super(createdAt, updatedAt);
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.proficiency = proficiency;
		this.positions = positions;
	}

	public static MyProfileUpdateResponse of(User user) {
		return new MyProfileUpdateResponse(user.getCreatedAt().toInstant(ZoneOffset.UTC), user.getUpdatedAt().toInstant(ZoneOffset.UTC),
			user.getId().toString(), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getProficiency(), user.getPositions());
	}

}
