package org.slams.server.court.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Texture;
import org.slams.server.user.entity.User;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class NewCourtInsertRequest {

    @NotNull(message ="name은 필수 값입니다.")
//    @Max(value=100,message = "name은 50글자 미만입니다.")
    private String name;

    @Positive(message = "위도는 0이상값입니다.")
    private double latitude;

    @Positive(message = "경도는 0이상값입니다.")
    private double longitude;

    private String image;

    private Texture texture;

    @Min(value=0,message = "농구골대는 0이상값입니다.")
    private int basketCount;

    // requestDto -> Entity
    public NewCourt toEntity(NewCourtInsertRequest request, User proposer) {
        return NewCourt.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .image(request.getImage())
                .texture(request.getTexture())
                .basketCount(request.getBasketCount())
                .proposer(proposer)
                .build();
    }

}
