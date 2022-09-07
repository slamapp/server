package org.slams.server.chat.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.common.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
public class ChatroomResponse extends BaseDto {
    @ApiModelProperty(value = "채팅방 구별키", required = true)
    private final String chatroomId;
    @ApiModelProperty(value = "채팅타입 [PERSONAL,GROUP,COURT]", required = true)
    private final ChatroomType chatroomType;
    @ApiModelProperty(value = "채팅방 이름", required = true)
    private final String chatroomName;
    @ApiModelProperty(value = "마지막 채팅 내용", required = true)
    private final String lastChat;

    @Builder
    public ChatroomResponse(
            String chatroomId,
            ChatroomType chatroomType,
            String chatroomName,
            String lastChat,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);
        this.chatroomId = chatroomId;
        this.chatroomType = chatroomType;
        this.chatroomName = chatroomName;
        this.lastChat = lastChat;
    }
}
