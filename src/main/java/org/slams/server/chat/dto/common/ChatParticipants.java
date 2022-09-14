package org.slams.server.chat.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ChatParticipants {
    @ApiModelProperty(value = "참여자 구별키", required = true)
    private final String participantId;
    @ApiModelProperty(value = "참여자의 타입", required = true)
    private final ChatroomParticipantsType typeOfParticipant;


    public ChatParticipants(
            String participantId,
            ChatroomParticipantsType typeOfParticipant
    ) {
        this.participantId = participantId;
        this.typeOfParticipant = typeOfParticipant;
    }
}
