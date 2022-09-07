package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
public class ResultOfCreatingOfChatroomResponse extends BaseDto {
    private final String chatroomId;

    @Builder
    public ResultOfCreatingOfChatroomResponse(
            String chatroomId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);
        this.chatroomId = chatroomId;
    }
}
