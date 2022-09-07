package org.slams.server.chat.convertor;

import org.slams.server.chat.dto.response.ResultOfCreatingOfCourtChatroomResponse;
import org.slams.server.chat.entity.UserChatroomMapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yunyun on 2021/12/16.
 */

@Component
public class ChatroomMappingConvertor {

    public List<ResultOfCreatingOfCourtChatroomResponse> toDtoList(List<UserChatroomMapping> userChatroomMappingList){
        return userChatroomMappingList.stream()
                .map(v -> toDto(v))
                .collect(Collectors.toList());
    }

    public ResultOfCreatingOfCourtChatroomResponse toDto(UserChatroomMapping userChatroomMapping){
        return ResultOfCreatingOfCourtChatroomResponse.builder()
                .courtId(userChatroomMapping.getCourtChatroomMapping().getCourt().getId().toString())
                .courtName(userChatroomMapping.getCourtChatroomMapping().getCourt().getName())
                .createdAt(userChatroomMapping.getCreatedAt())
                .updatedAt(userChatroomMapping.getCourtChatroomMapping().getUpdatedAt())
                .build();
    }
}
