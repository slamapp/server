package org.slams.server.favorite.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.favorite.entity.Favorite;

import java.time.Instant;
import java.time.ZoneOffset;

@Getter
public class FavoriteInsertResponse extends BaseResponse {

	private String id;
	private CourtInMapDto court;

	private FavoriteInsertResponse(Instant createdAt, Instant updatedAt, String id, CourtInMapDto court) {
		super(createdAt, updatedAt);
		this.id = id;
		this.court = court;
	}

	public static FavoriteInsertResponse toResponse(Favorite favorite) {
		return new FavoriteInsertResponse(favorite.getCreatedAt().toInstant(ZoneOffset.UTC), favorite.getUpdatedAt().toInstant(ZoneOffset.UTC),
			favorite.getId().toString(), CourtInMapDto.toDto(favorite.getCourt()));
	}

}
