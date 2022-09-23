package org.slams.server.chat.service;

import lombok.RequiredArgsConstructor;
import org.slams.server.chat.convertor.ChatroomMappingConvertor;
import org.slams.server.chat.dto.common.*;
import org.slams.server.chat.dto.response.*;
import org.slams.server.chat.entity.CourtChatroomMapping;
import org.slams.server.chat.entity.UserChatroomMapping;
import org.slams.server.chat.repository.CourtChatroomMappingRepository;
import org.slams.server.chat.repository.UserChatroomMappingRepository;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.court.entity.Court;
import org.slams.server.court.exception.CourtNotFoundException;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.user.entity.User;
import org.slams.server.user.exception.UserNotFoundException;
import org.slams.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by yunyun on 2021/12/16.
 */

@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final UserChatroomMappingRepository userChatRoomMappingRepository;
    private final ChatroomMappingConvertor chatroomMappingConvertor;
    private final CourtChatroomMappingRepository courtChatroomMappingRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;

    /** 채팅방 생성 -> 농구장 생성시 함께 생성됨 **/
    public void saveChatRoom(Long courtId){

        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new CourtNotFoundException("해당 농구장이 존재하지 않습니다."));

        courtChatroomMappingRepository.save(
                CourtChatroomMapping.of(court)
        );
    }

    /** 채팅방 최초 입장 **/
    public ResultOfCreatingOfChatroomToParticipateInResponse saveUserChatroom(Long userId, String chatroomId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 사용자는 존재하지 않습니다."));

        return ResultOfCreatingOfChatroomToParticipateInResponse.builder()
                .id()
                .name()
                .admins()
                .type()
                .participants()
                .lastChat()
        .build();
    }
    public ResultOfCreatingOfCourtChatroomToParticipateInResponse saveUserCourtChatroom(Long userId, String courtId){
        Long courtIdConvertedIntoLong = Long.valueOf(courtId);
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new UserNotFoundException("해당 사용자는 존재하지 않습니다."));
        CourtChatroomMapping courtChatroomMapping = courtChatroomMappingRepository.findByCourtId(courtIdConvertedIntoLong);
        return new ResultOfCreatingOfCourtChatroomToParticipateInResponse(null, null, null, null, null);
    }

    public List<ChatroomResponse> findUserChatroomListByUserId(Long userId, CursorPageRequest cursorRequest){
        return List.of(
                null
        );
    }

    public void deleteUserChatroomByCourtId(String courtId, Long userId){
            userChatRoomMappingRepository.deleteUserChatRoomByCourtId(Long.valueOf(courtId), userId);
    }

    public List<UserChatroomMapping> cursorPageForFindAllByUserId(Long userId, CursorPageRequest cursorRequest){
        PageRequest pageable = PageRequest.of(0, cursorRequest.getSize());
        return cursorRequest.getIsFirst() ?
                userChatRoomMappingRepository.findAllByUserIdByCreated(userId, pageable):
                userChatRoomMappingRepository.findAllByUserIdMoreThenLastIdByCreated(userId, cursorRequest.getLastIdParedForLong(), pageable);
    }

    public String findLastId(String userId, CursorPageRequest cursorRequest){
        PageRequest pageable = PageRequest.of(0, cursorRequest.getSize());
        List<Long> ids = cursorRequest.getIsFirst() ?
                userChatRoomMappingRepository.findIdByUserIdByCreated(Long.valueOf(userId), pageable):
                userChatRoomMappingRepository.findIdByUserIdMoreThenLastIdByCreated(Long.valueOf(userId), cursorRequest.getLastIdParedForLong(), pageable);

        // 빈 배열 일 때
        if (ids.size() -1 < 0) {
            return null;
        }else{
            // 마지막 데이터 인지 확인
            if (cursorRequest.getSize() > ids.size()){
                // 마지막 데이터 일 때
                return null;
            }else {
                // 마지막 데이터가 아닐 때
                return String.valueOf(ids.get(ids.size() - 1));
            }

        }

    }
}
