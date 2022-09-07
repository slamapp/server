package org.slams.server.chat.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class CourtChatroom extends ChatroomCommon {

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
