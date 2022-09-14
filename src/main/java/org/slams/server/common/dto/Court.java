package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.court.entity.Texture;
import org.slams.server.notification.common.ValidationMessage;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by yunyun on 2022/01/24.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Court {
    @ApiModelProperty(value = "농구장 정보 구별키", required = true)
    private final String id;
    @ApiModelProperty(value = "농구장 이름", required = true)
    private final String name;
    @ApiModelProperty(value = "농구장 위도 ", required = true)
    private final double latitude;
    @ApiModelProperty(value = "농구장 경도", required = true)
    private final double longitude;
    @ApiModelProperty(value = "농구장 이미지", required = true)
    private final String image;
    @ApiModelProperty(value = "농구공 보유 개수", required = true)
    private final int basketCount;
    @ApiModelProperty(value = "농구장 바닥 재질", required = true)
    private final Texture texture;

    @Builder
    public Court(
            String id,
            String name,
            double latitude,
            double longitude,
            String image,
            int basketCount,
            Texture texture
    ){
        checkArgument(id != null, ValidationMessage.NOTNULL_ID);
        checkArgument(isNotEmpty(name), ValidationMessage.NOT_EMPTY_NAME);
        checkArgument(basketCount > 0, ValidationMessage.MORE_THAN_ONE_BASKET_COUNT);

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.basketCount = basketCount;
        this.texture = texture;
    }
}
