package org.slams.server.chat.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.Chatroom;

@Getter
public class ResultOfCreatingOfChatroomToParticipateInResponse {
    @ApiModelProperty(value = "참여할 채팅방 정보", required = true)
    private final Chatroom chatroom;

    @Builder
    public ResultOfCreatingOfChatroomToParticipateInResponse(
            Chatroom chatroom
    ) {
        this.chatroom = chatroom;
    }
}
