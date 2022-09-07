package org.slams.server.chat.dto.common;


public class Chat {
    private final String id;
    private final String text;
    private final ChatType type;

    public Chat(
            String id,
            String text,
            ChatType type
    ) {
        this.id = id;
        this.text = text;
        this.type = type;
    }

}
