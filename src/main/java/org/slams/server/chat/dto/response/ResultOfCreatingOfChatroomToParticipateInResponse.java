package org.slams.server.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.*;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class ResultOfCreatingOfChatroomToParticipateInResponse extends Chatroom {

    @Builder
    public ResultOfCreatingOfChatroomToParticipateInResponse(
            String id,
            String name,
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat
    ) {
        super(id, name, admins, type, participants, lastChat);
    }
}
