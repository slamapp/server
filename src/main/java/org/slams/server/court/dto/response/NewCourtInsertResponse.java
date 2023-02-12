package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class NewCourtInsertResponse extends BaseResponse {

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

	private NewCourtInsertResponse(Instant createdAt, Instant updatedAt,
								   String id, String name, double latitude, double longitude,
								   String image, Texture texture, int basketCount, Status status) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.texture = texture;
		this.basketCount = basketCount;
		this.status = status;
	}

	public static NewCourtInsertResponse toResponse(NewCourt newCourt) {
		return new NewCourtInsertResponse(newCourt.getCreatedAt().toInstant(ZoneOffset.UTC), newCourt.getUpdatedAt().toInstant(ZoneOffset.UTC),
			newCourt.getId().toString(), newCourt.getName(), newCourt.getLatitude(), newCourt.getLongitude(),
			newCourt.getImage(), newCourt.getTexture(), newCourt.getBasketCount(), newCourt.getStatus());
	}

}
