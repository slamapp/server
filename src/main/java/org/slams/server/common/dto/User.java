package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.defaultDto.DefaultUser;
import org.slams.server.common.dto.referenceDto.BaseReferenceDto;
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
public class User extends DefaultUser implements BaseReferenceDto {
    @Builder
    public User(
            String id,
            String nickname,
            String profileImage
    ){
        super(id, nickname, profileImage);
    }
}
