package org.slams.server.chat.dto.common;

import lombok.Getter;

@Getter
public class Admin {
    private final String id;
    private final ChatroomParticipantsType participantsType;

    public Admin(
            String id,
            ChatroomParticipantsType participantsType
    ) {
        this.id = id;
        this.participantsType = participantsType;
    }
}
