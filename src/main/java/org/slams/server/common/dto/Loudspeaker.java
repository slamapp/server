package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.notification.common.ValidationMessage;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;


@Getter
public class Loudspeaker {

    @ApiModelProperty(value = "확성기 구별키", required = true)
    private final String id;
    @ApiModelProperty(value = "확성기 생성자", required = true)
    private final User creator;
    @ApiModelProperty(value = "확성기를 요청한 농구장 정보", required = true)
    private final Court court;

    @ApiModelProperty(value = "농구 시작 시간", required = true)
    private final LocalDateTime startTime;

    @Builder
    public Loudspeaker(
            String id,
            User creator,
            Court court,
            LocalDateTime startTime
    ){
        checkArgument(id != null, ValidationMessage.NOTNULL_ID);
        checkArgument(court != null, ValidationMessage.NOTNULL_COURT);
        checkArgument(startTime != null, ValidationMessage.NOTNULL_START_TIME);

        this.id = id;
        this.creator = creator;
        this.court = court;
        this.startTime = startTime;
    }

}
