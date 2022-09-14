package org.slams.server.chat.dto.common;

import lombok.Getter;

@Getter
public class Admin {
    private final String adminId;
    private final ChatroomParticipantsType chatroomParticipantsType;

    public Admin(
            String adminId,
            ChatroomParticipantsType chatroomParticipantsType
    ) {
        this.adminId = adminId;
        this.chatroomParticipantsType = chatroomParticipantsType;
    }
}
