package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;
import org.slams.server.user.dto.response.SupervisorDto;
import org.slams.server.user.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class NewCourtResponse extends BaseResponse {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String image;
	private Texture texture;
	private int basketCount;
	private Status status;
	private SupervisorDto supervisor;

	private NewCourtResponse(Instant createdAt, Instant updatedAt,
							 String id, String name, double latitude, double longitude,
							 String image, Texture texture, int basketCount, Status status, SupervisorDto supervisor) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.texture = texture;
		this.basketCount = basketCount;
		this.status = status;
		this.supervisor = supervisor;
	}

	public static NewCourtResponse toResponse(NewCourt newCourt, User supervisor) {
		return new NewCourtResponse(newCourt.getCreatedAt().toInstant(ZoneOffset.UTC), newCourt.getUpdatedAt().toInstant(ZoneOffset.UTC),
			newCourt.getId().toString(), newCourt.getName(), newCourt.getLatitude(), newCourt.getLongitude(),
			newCourt.getImage(), newCourt.getTexture(), newCourt.getBasketCount(), newCourt.getStatus(), SupervisorDto.toDto(supervisor));
	}

}
