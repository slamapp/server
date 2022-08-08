package org.slams.server.reservation.dto.response;

import lombok.Getter;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.follow.entity.Follow;
import org.slams.server.user.dto.response.BriefUserInfoDto;
import org.slams.server.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class ReservationDetailResponse {

    private BriefUserInfoDto user;
    private String followId;

    private ReservationDetailResponse(BriefUserInfoDto user, String followId) {
        this.user = user;
        this.followId = followId;
    }

    public static ReservationDetailResponse of(User user, String followId){
        return new ReservationDetailResponse(BriefUserInfoDto.toDto(user), followId);
    }

}
