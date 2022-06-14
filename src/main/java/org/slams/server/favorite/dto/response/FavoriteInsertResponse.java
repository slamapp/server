package org.slams.server.favorite.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.dto.response.CourtInMapDto;
import org.slams.server.favorite.entity.Favorite;

import java.time.LocalDateTime;

@Getter
public class FavoriteInsertResponse extends BaseResponse {

    private String id;
    private CourtInMapDto court;

    private FavoriteInsertResponse(LocalDateTime createdAt, LocalDateTime updatedAt, String id, CourtInMapDto court) {
        super(createdAt, updatedAt);
        this.id = id;
        this.court = court;
    }

    public static FavoriteInsertResponse of(Favorite favorite){
        return new FavoriteInsertResponse(
            favorite.getCreatedAt(), favorite.getUpdatedAt(), favorite.getId().toString(), CourtInMapDto.toDto(favorite.getCourt()));
    }

}
