package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.defaultDto.DefaultCourt;
import org.slams.server.common.dto.referenceDto.BaseReferenceDto;
import org.slams.server.court.entity.Texture;
import org.slams.server.notification.common.ValidationMessage;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by yunyun on 2022/01/24.
 */
@Getter
public class Court extends DefaultCourt implements BaseReferenceDto {


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
        super(id, name, latitude, longitude, image, basketCount, texture);
    }
}
