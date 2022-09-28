package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.defaultDto.DefaultLoudspeaker;
import org.slams.server.common.dto.referenceDto.BaseReferenceDto;
import org.slams.server.notification.common.ValidationMessage;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;


@Getter
public class Loudspeaker extends DefaultLoudspeaker implements BaseReferenceDto {

    @Builder
    public Loudspeaker(
            String id,
            User creator,
            Court court,
            LocalDateTime startTime
    ){
        super(id, creator, court, startTime);
    }

}
