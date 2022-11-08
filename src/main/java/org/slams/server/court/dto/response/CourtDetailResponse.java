package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;

import java.time.LocalDateTime;

@Getter
public class CourtDetailResponse extends BaseResponse {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String image;
	private int basketCount;
	private Texture texture;
	private Long reservationMaxCount;

	public CourtDetailResponse(String id, String name, double latitude, double longitude,
							   String image, int basketCount, Texture texture,
							   LocalDateTime createdAt, LocalDateTime updatedAt, Long reservationMaxCount) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.basketCount = basketCount;
		this.texture = texture;
		this.reservationMaxCount = reservationMaxCount;
	}

	public static CourtDetailResponse toResponse(Court court, Long reservationMaxCount) {
		return new CourtDetailResponse(court.getId().toString(), court.getName(), court.getLatitude(), court.getLongitude(),
			court.getImage(), court.getBasketCount(), court.getTexture(), court.getCreatedAt(), court.getUpdatedAt(), reservationMaxCount);
	}

}