package org.slams.server.chat.dto.common;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class Chat {
    @ApiModelProperty(value = "채팅 구별키", required = true)
    private final String id;
    @ApiModelProperty(value = "채팅 내용", required = true)
    private final String text;
    @ApiModelProperty(value = "채팅 타입", required = true)
    private final ChatType type;

    @ApiModelProperty(value = "채팅 숨김 여부", required = true)
    private final boolean isHidden;

    public Chat(
            String id,
            String text,
            ChatType type,
            boolean isHidden
    ) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.isHidden = isHidden;
    }

}
