package org.slams.server.chat.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class CourtChatroom extends Chatroom {

    @ApiModelProperty(value = "채팅방의 농구장 구별키", required = true)
    private final String courtId;


    @Builder
    public CourtChatroom(
            List<String> admins,
            ChatroomType type,
            List<Participant> participants,
            Chat lastChat,
            String courtId
    ) {
        super(admins, type, participants, lastChat);
        this.courtId = courtId;
    }
}
