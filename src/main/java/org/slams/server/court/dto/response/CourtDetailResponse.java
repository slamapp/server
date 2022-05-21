package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;

@Getter
public class CourtDetailResponse {

    private CourtDetailDto court;
    private Long reservationMaxCount;

    private CourtDetailResponse(CourtDetailDto court, Long reservationMaxCount){
        this.court = court;
        this.reservationMaxCount = reservationMaxCount;
    }

    public static CourtDetailResponse toResponse(Court court, Long reservationMaxCount){
        return new CourtDetailResponse(CourtDetailDto.toDto(court), reservationMaxCount);
    }

}