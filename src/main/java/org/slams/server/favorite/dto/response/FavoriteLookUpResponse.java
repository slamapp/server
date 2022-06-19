package org.slams.server.favorite.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.favorite.entity.Favorite;

import java.time.LocalDateTime;

@Getter
public class FavoriteLookUpResponse extends BaseResponse {

	private String id;
	private CourtInMapDto court;

	private FavoriteLookUpResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, CourtInMapDto court) {
		super(createdAt, updatedAt);
		this.id = id;
		this.court = court;
	}

	public static FavoriteLookUpResponse toResponse(Favorite favorite) {
		return new FavoriteLookUpResponse(
			favorite.getCreatedAt(), favorite.getUpdatedAt(), favorite.getId().toString(), CourtInMapDto.toDto(favorite.getCourt()));
	}

}
