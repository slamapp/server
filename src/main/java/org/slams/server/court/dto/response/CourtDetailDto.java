package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;

import java.time.LocalDateTime;

@Getter
public class CourtDetailDto extends BaseResponse {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String image;
	private int basketCount;
	private Texture texture;

	private CourtDetailDto(String id, String name, double latitude, double longitude,
						   String image, int basketCount, Texture texture,
						   LocalDateTime createdAt, LocalDateTime updatedAt) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.basketCount = basketCount;
		this.texture = texture;
	}

	public static CourtDetailDto toDto(Court court){
		return new CourtDetailDto(court.getId().toString(), court.getName(), court.getLatitude(), court.getLongitude(),
			court.getImage(), court.getBasketCount(), court.getTexture(), court.getCreatedAt(), court.getUpdateAt());
	}

}