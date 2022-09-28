package org.slams.server.chat.dto.common;

import lombok.Getter;

@Getter
public class Participant {
    private final String id;
    private final TypeOfParticipants type;

    public Participant(
            String id,
            TypeOfParticipants type
    ) {
        this.id = id;
        this.type = type;
    }
}
