package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;

import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/12/16.
 */

@Getter
public class ResultOfCreatingOfCourtChatroomResponse extends BaseDto {
    private final String chatroomId;
    private final String courtId;
    private final String courtName;


    @Builder
    public ResultOfCreatingOfCourtChatroomResponse(
            String chatroomId,
            String courtId,
            String courtName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        super(createdAt, updatedAt);
        this.chatroomId = chatroomId;
        this.courtId = courtId;
        this.courtName = courtName;

    }
}
