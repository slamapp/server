package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.Admin;
import org.slams.server.chat.dto.common.Chat;
import org.slams.server.chat.dto.common.Chatroom;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.common.dto.User;

import java.util.List;


@Getter
public class ChatroomResponse extends Chatroom {

    @Builder
    public ChatroomResponse(List<Admin> admins, ChatroomType type, List<User> participants, Chat lastChat) {
        super(admins, type, participants, lastChat);
    }
}
