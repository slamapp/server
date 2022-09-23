package org.slams.server.chat.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class CourtChatroom extends Chatroom {

    @ApiModelProperty(value = "채팅방의 농구장 정보", required = true)
    private final Court court;


    @Builder
    public CourtChatroom(
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat,
            Court court
    ) {
        super(admins, type, participants, lastChat);
        this.court = court;
    }
}
