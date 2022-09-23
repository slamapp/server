package org.slams.server.chat.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
public class ResultOfCreatingOfChatroomResponse extends BaseDto {
    @ApiModelProperty(value = "생성된 채팅방 구별키", required = true)
    private final String id;

    @Builder
    public ResultOfCreatingOfChatroomResponse(
            String id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);
        this.id = id;
    }
}
