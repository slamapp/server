package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.notification.common.ValidationMessage;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by yunyun on 2022/01/24.
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseDto{

    @ApiModelProperty(value = "사용자 구별키", required = true)
    private String id;
    @ApiModelProperty(value = "사용자 닉네임", required = true)
    private String nickname;
    @ApiModelProperty(value = "사용자 프로필 이미지", required = true)
    private String profileImage;

    @Builder
    public User(
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
