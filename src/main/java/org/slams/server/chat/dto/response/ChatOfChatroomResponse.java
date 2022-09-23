package org.slams.server.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.slams.server.chat.dto.common.Chat;
import org.slams.server.chat.dto.common.ChatType;
import org.slams.server.common.dto.Loudspeaker;

/**
 * Created by yunyun on 2021/12/16.
 */

@Getter
public class ChatOfChatroomResponse extends Chat {
    @ApiModelProperty(value = "확성기 정보")
    @JsonProperty("loudSpeaker")
    private final Loudspeaker loudspeaker;

    @Builder
    public ChatOfChatroomResponse(
            String id,
            String text,
            ChatType type,
            String creatorId,
            boolean isHidden,
            Loudspeaker loudspeaker
    ) {
        super(id, text, type, creatorId, isHidden);
        this.loudspeaker = loudspeaker;
    }

}
