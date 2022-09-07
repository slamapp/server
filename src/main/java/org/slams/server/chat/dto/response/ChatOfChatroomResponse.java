package org.slams.server.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.ChatType;
import org.slams.server.chat.dto.common.Chatroom;
import org.slams.server.common.dto.BaseDto;
import org.slams.server.common.dto.Loudspeaker;

/**
 * Created by yunyun on 2021/12/16.
 */

@Getter
public class ChatOfChatroomResponse extends BaseDto {

    @ApiModelProperty(value = "채팅방 구별키", required = true)
    private final String chatroomId;
    @ApiModelProperty(value = "채팅 글의 구별키", required = true)
    private final String chatId;

    @ApiModelProperty(value = "채팅 내용", required = true)
    private final String text;
    @ApiModelProperty(value = "채팅 ", required = true)
    private final ChatType type;

    @ApiModelProperty(value = "확성기 정보")
    @JsonProperty("loudSpeaker")
    private final Loudspeaker loudspeaker;


    @Builder
    public ChatOfChatroomResponse(
            String chatroomId,
            String chatId,
            String text,
            ChatType type,
            Loudspeaker loudspeaker
    ){
        this.chatroomId = chatroomId;
        this.chatId = chatId;
        this.text = text;
        this.type = type;
        this.loudspeaker = loudspeaker;
    }

}
