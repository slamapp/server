package org.slams.server.chat.dto.common;

public class Admin {
    private final String id;
    private final ChatroomMemberType chatroomMemberType;

    public Admin(
            String id,
            ChatroomMemberType chatroomMemberType
    ) {
        this.id = id;
        this.chatroomMemberType = chatroomMemberType;
    }
}
