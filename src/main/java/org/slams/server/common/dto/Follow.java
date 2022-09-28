package org.slams.server.common.dto;

/**
 * Created by yunyun on 2022/01/24.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.defaultDto.DefaultFollow;
import org.slams.server.common.dto.referenceDto.BaseReferenceDto;

@Getter
public class Follow extends DefaultFollow {
    @Builder
    public Follow(
            User sender,
            User receiver
    ){
        super(sender, receiver);
    }

}
