package org.slams.server.chat.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Created by yunyun on 2021/12/18.
 */

@Getter
public class ResultOfDeletingUserChatroomResponse {

    @ApiModelProperty(value = "채팅방에서 나온 채팅방의 구별키", required = true)
    private final List<String> chatroomIds;

    @Builder
    public ResultOfDeletingUserChatroomResponse(List<String> chatroomIds){
        this.chatroomIds = chatroomIds;
    }
}
