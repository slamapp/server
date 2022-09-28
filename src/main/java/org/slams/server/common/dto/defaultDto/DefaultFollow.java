package org.slams.server.common.dto.defaultDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;
import org.slams.server.common.dto.User;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultFollow extends BaseDto {
    @ApiModelProperty(value = "팔로우 한 사람", required = true)
    private User sender;
    @ApiModelProperty(value = "팔로우 당한 사람", required = true)
    private User receiver;


    public DefaultFollow(
            User sender,
            User receiver
    ){
        this.sender = sender;
        this.receiver = receiver;
    }
}
