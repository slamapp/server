package org.slams.server.chat.dto.common;



import lombok.Getter;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class ChatroomCommon {
    private final List<Admin> admins;
    private final ChatroomType type;
    private final List<User> participants;
    private final Chat lastChat;


    public ChatroomCommon(
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat
    ) {
        this.admins = admins;
        this.type = type;
        this.participants = participants;
        this.lastChat = lastChat;
    }

}
