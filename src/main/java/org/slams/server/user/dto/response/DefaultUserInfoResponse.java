package org.slams.server.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.notification.dto.response.NotificationResponse;
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
public class DefaultUserInfoResponse {

	private String id;
	private String nickname;
	private String profileImage;
	private String description;
	private Role role;
	private Proficiency proficiency;
	private List<Position> positions = new ArrayList<>();
	private List<NotificationResponse> notifications;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private DefaultUserInfoResponse(String id, String nickname, String profileImage,
									String description, Role role, Proficiency proficiency, List<Position> positions,
									List<NotificationResponse> notifications,
									LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.description = description;
		this.role = role;
		this.proficiency = proficiency;
		this.positions = positions;
		this.notifications = notifications;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static DefaultUserInfoResponse toResponse(User user, List<NotificationResponse> notifications) {
		return new DefaultUserInfoResponse(String.valueOf(user.getId()), user.getNickname(), user.getProfileImage(),
			user.getDescription(), user.getRole(), user.getProficiency(), user.getPositions(), notifications,
			user.getCreatedAt(), user.getUpdatedAt());
	}

}
