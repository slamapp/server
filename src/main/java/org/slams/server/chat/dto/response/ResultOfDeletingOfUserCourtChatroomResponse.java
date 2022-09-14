package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResultOfDeletingOfUserCourtChatroomResponse {

    private final String chatroomId;
    private final String courtId;

    @Builder
    public ResultOfDeletingOfUserCourtChatroomResponse(
            String chatroomId,
            String courtId
    ) {
        this.chatroomId = chatroomId;
        this.courtId = courtId;
    }

}
