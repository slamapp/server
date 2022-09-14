package org.slams.server.chat.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.slams.server.chat.dto.common.ChatParticipants;
import org.slams.server.chat.dto.common.ChatroomType;

import java.util.List;

@Getter
public class CreateChatroomRequest {
    @ApiModelProperty(value = "채팅방 이름")
    private final String chatroomName;
    @ApiModelProperty(value = "채팅방 타입", required = true)
    private final ChatroomType chatroomType;
    @ApiModelProperty(value = "채팅방 참여자", required = true)
    private final List<ChatParticipants> participants;

    public CreateChatroomRequest(
            String chatroomName,
            ChatroomType chatroomType,
            List<ChatParticipants> participants
    ) {
        this.chatroomName = chatroomName;
        this.chatroomType = chatroomType;
        this.participants = participants;
    }
}
