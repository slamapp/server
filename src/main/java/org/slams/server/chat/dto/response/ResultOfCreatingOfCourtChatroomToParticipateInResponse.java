package org.slams.server.chat.dto.response;

import lombok.Getter;
import org.slams.server.chat.dto.common.CourtChatroom;

@Getter
public class ResultOfCreatingOfCourtChatroomToParticipateInResponse {
    private final CourtChatroom chatroom;

    public ResultOfCreatingOfCourtChatroomToParticipateInResponse(
            CourtChatroom chatroom
    ) {
        this.chatroom = chatroom;
    }
}
