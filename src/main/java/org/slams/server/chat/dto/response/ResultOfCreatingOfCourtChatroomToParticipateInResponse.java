package org.slams.server.chat.dto.response;

import lombok.Getter;
import org.slams.server.chat.dto.common.Participant;
import org.slams.server.chat.dto.common.Chat;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.chat.dto.common.CourtChatroom;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class ResultOfCreatingOfCourtChatroomToParticipateInResponse extends CourtChatroom{

    public ResultOfCreatingOfCourtChatroomToParticipateInResponse(List<String> admins, ChatroomType type, List<Participant> participants, Chat lastChat, String courtId) {
        super(admins, type, participants, lastChat, courtId);
    }
}
