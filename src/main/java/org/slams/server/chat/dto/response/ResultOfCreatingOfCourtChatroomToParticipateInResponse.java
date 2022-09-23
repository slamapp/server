package org.slams.server.chat.dto.response;

import lombok.Getter;
import org.slams.server.chat.dto.common.Admin;
import org.slams.server.chat.dto.common.Chat;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.chat.dto.common.CourtChatroom;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class ResultOfCreatingOfCourtChatroomToParticipateInResponse extends CourtChatroom{

    public ResultOfCreatingOfCourtChatroomToParticipateInResponse(List<Admin> admins, ChatroomType type, List<User> participants, Chat lastChat, Court court) {
        super(admins, type, participants, lastChat, court);
    }
}
