package org.slams.server.chat.dto.common;



import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.slams.server.common.dto.BaseDto;
import org.slams.server.common.dto.User;

import java.util.List;

@Getter
public class Chatroom extends BaseDto {
    @ApiModelProperty(value = "채팅방 구별키", required = true)
    private String id;
    @ApiModelProperty(value = "채팅방 이름")
    private String name;
    @ApiModelProperty(value = "채팅 관리자(사용자)의 구별키", required = true)
    private final List<String> admins;
    @ApiModelProperty(value = "채팅타입 [PERSONAL,GROUP,COURT]", required = true)
    private final ChatroomType type;
    @ApiModelProperty(value = "채팅 맴버", required = true)
    private final List<Participant> participants;
    @ApiModelProperty(value = "마지막 채팅 내용", required = true)
    private final Chat lastChat;


    public Chatroom(
            String id,
            String name,
            List<String> admins,
            ChatroomType type,
            List<Participant> participants,
            Chat lastChat
    ) {
        this.id = id;
        this.name = name;
        this.admins = admins;
        this.type = type;
        this.participants = participants;
        this.lastChat = lastChat;
    }

    public Chatroom(
            List<String> admins,
            ChatroomType type,
            List<Participant> participants,
            Chat lastChat
    ) {
        this.admins = admins;
        this.type = type;
        this.participants = participants;
        this.lastChat = lastChat;
    }

}
