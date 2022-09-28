package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.Participant;
import org.slams.server.chat.dto.common.Chat;
import org.slams.server.chat.dto.common.Chatroom;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.common.dto.User;

import java.util.List;


@Getter
public class ChatroomResponse extends Chatroom {

    @Builder
    public ChatroomResponse(List<String> admins, ChatroomType type, List<Participant> participants, Chat lastChat) {
        super(admins, type, participants, lastChat);
    }
}
