package org.slams.server.court.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;
import org.slams.server.user.dto.response.CreatorDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCourtInReadyLookUpResponse {

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
	private CreatorDto creator;

	private NewCourtInReadyLookUpResponse(String id, String name, double latitude, double longitude,
										  String image, Texture texture, int basketCount, Status status,
										  LocalDateTime createdAt, LocalDateTime updatedAt, CreatorDto creator) {
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
		this.creator = creator;
	}

	public static NewCourtInReadyLookUpResponse toResponse(NewCourt newCourt) {
		return new NewCourtInReadyLookUpResponse(newCourt.getId().toString(), newCourt.getName(), newCourt.getLatitude(), newCourt.getLongitude(),
			newCourt.getImage(), newCourt.getTexture(), newCourt.getBasketCount(), newCourt.getStatus(),
			newCourt.getCreatedAt(), newCourt.getUpdatedAt(), CreatorDto.toDto(newCourt.getProposer()));
	}

}
