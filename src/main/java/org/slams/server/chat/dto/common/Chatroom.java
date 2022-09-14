package org.slams.server.chat.dto.common;

import io.swagger.annotations.ApiModelProperty;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.User;

import java.util.List;

public interface Chatroom {

    @ApiModelProperty(value = "채팅방 구별키", required = true)
    public String getId();

    @ApiModelProperty(value = "채팅방 이름")
    public String getName();

    @ApiModelProperty(value = "관리자 정보", required = true)
    public List<Admin> getAdmins();

    @ApiModelProperty(value = "채팅방 타입", required = true)
    public ChatroomType getType();

    @ApiModelProperty(value = "채팅방 참여자", required = true)
    public List<User> getParticipants();

    @ApiModelProperty(value = "채팅방의 마지막 채팅 내용", required = true)
    public Chat getLastChat();

    @ApiModelProperty(value = "농구장 정보")
    public Court getCourt();
}
