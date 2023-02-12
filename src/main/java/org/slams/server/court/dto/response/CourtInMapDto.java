package org.slams.server.court.dto.response;

import lombok.*;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.Court;

import java.time.Instant;
import java.time.ZoneOffset;

@Getter
public class CourtInMapDto extends BaseResponse {

	private String id;
	private String name;
	private double latitude;
	private double longitude;

	private CourtInMapDto(Instant createdAt, Instant updatedAt, String id, String name, double latitude, double longitude) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static CourtInMapDto toDto(Court court) {
		return new CourtInMapDto(court.getCreatedAt().toInstant(ZoneOffset.UTC), court.getUpdatedAt().toInstant(ZoneOffset.UTC),
		court.getId().toString(), court.getName(), court.getLatitude(), court.getLongitude());
	}

}
