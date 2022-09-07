package org.slams.server.chat.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class UserChatroom extends ChatroomCommon {

    @Builder
    public UserChatroom(
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat
    ) {
        super(admins, type, participants, lastChat);
    }
}
