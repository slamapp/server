package org.slams.server.chat.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.ChatroomCommon;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.common.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
public class ChatroomResponse extends BaseDto {
    private final ChatroomCommon chatroom;

    @Builder
    public ChatroomResponse(
            ChatroomCommon chatroom
    ) {
       this.chatroom = chatroom;
    }
}
