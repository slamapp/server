package org.slams.server.common.dto;

/**
 * Created by yunyun on 2022/01/24.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Follow extends BaseDto{
    @ApiModelProperty(value = "팔로우 한 사람", required = true)
    private User sender;
    @ApiModelProperty(value = "팔로우 당한 사람", required = true)
    private User receiver;

    @Builder
    public Follow(
            User sender,
            User receiver
    ){
        this.sender = sender;
        this.receiver = receiver;
    }

}
