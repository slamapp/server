package org.slams.server.chat.dto.common;



import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class ChatroomCommon {
    @ApiModelProperty(value = "채팅방 구별키", required = true)
    private String id;
    @ApiModelProperty(value = "채팅방 이름")
    private String name;
    @ApiModelProperty(value = "채팅 관리자", required = true)
    private final List<Admin> admins;
    @ApiModelProperty(value = "채팅타입 [PERSONAL,GROUP,COURT]", required = true)
    private final ChatroomType type;

    @ApiModelProperty(value = "채팅 맴버", required = true)
    private final List<User> participants;
    @ApiModelProperty(value = "마지막 채팅 내용", required = true)
    private final Chat lastChat;


    private ChatroomCommon(
            String id,
            String name,
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat
    ) {
        this.id = id;
        this.name = name;
        this.admins = admins;
        this.type = type;
        this.participants = participants;
        this.lastChat = lastChat;
    }

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

    public static ChatroomCommon of(
            String id,
            String name,
            List<Admin> admins,
            ChatroomType type,
            List<User> participants,
            Chat lastChat
    ) {
        return new ChatroomCommon(id, name, admins, type, participants, lastChat);
    }

}
