package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;
import org.slams.server.user.dto.response.SupervisorDto;
import org.slams.server.user.entity.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtResponse {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String image;
	private Texture texture;
	private int basketCount;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private SupervisorDto supervisor;

	private NewCourtResponse(String id, String name, double latitude, double longitude,
							 String image, Texture texture, int basketCount, Status status,
							 LocalDateTime createdAt, LocalDateTime updatedAt, SupervisorDto supervisor) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.texture = texture;
		this.basketCount = basketCount;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.supervisor = supervisor;
	}

	public static NewCourtResponse toResponse(NewCourt newCourt, User supervisor){
		return new NewCourtResponse(newCourt.getId().toString(), newCourt.getName(), newCourt.getLatitude(), newCourt.getLongitude(),
			newCourt.getImage(), newCourt.getTexture(), newCourt.getBasketCount(), newCourt.getStatus(),
			newCourt.getCreatedAt(), newCourt.getUpdateAt(), SupervisorDto.toDto(supervisor));
	}

}
