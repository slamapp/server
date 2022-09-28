package org.slams.server.chat.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ChatParticipants {
    @ApiModelProperty(value = "참여자 구별키", required = true)
    private final String id;
    @ApiModelProperty(value = "참여자의 타입", required = true)
    private final TypeOfParticipants typeOfParticipant;


    public ChatParticipants(
            String id,
            TypeOfParticipants typeOfParticipant
    ) {
        this.id = id;
        this.typeOfParticipant = typeOfParticipant;
    }
}
