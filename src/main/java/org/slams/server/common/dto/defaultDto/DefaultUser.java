package org.slams.server.common.dto.defaultDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;
import org.slams.server.notification.common.ValidationMessage;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultUser extends BaseDto {
    @ApiModelProperty(value = "사용자 구별키", required = true)
    private String id;
    @ApiModelProperty(value = "사용자 닉네임", required = true)
    private String nickname;
    @ApiModelProperty(value = "사용자 프로필 이미지", required = true)
    private String profileImage;


    public DefaultUser(
            String id,
            String nickname,
            String profileImage
    ){
        checkArgument(id != null, ValidationMessage.NOTNULL_ID);
        checkArgument(isNotEmpty(nickname), ValidationMessage.NOT_EMPTY_NICKNAME);

        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;

    }
}
