package org.slams.server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.notification.dto.response.NotificationResponse;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultUserInfoResponse extends BaseResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Role role;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();
	private List<NotificationResponse> notifications;

	private DefaultUserInfoResponse(Instant createdAt, Instant updatedAt,
									String id, String nickname, String profileImage, String description,
									Role role, Proficiency proficiency, List<Position> positions, List<NotificationResponse> notifications) {
		super(createdAt, updatedAt);
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.role = role;
		this.proficiency = proficiency;
		this.positions = positions;
		this.notifications = notifications;
	}

	public static DefaultUserInfoResponse toResponse(User user, List<NotificationResponse> notifications) {
		return new DefaultUserInfoResponse(user.getCreatedAt().toInstant(ZoneOffset.UTC), user.getUpdatedAt().toInstant(ZoneOffset.UTC),
			String.valueOf(user.getId()), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getRole(), user.getProficiency(), user.getPositions(), notifications);
	}

}
