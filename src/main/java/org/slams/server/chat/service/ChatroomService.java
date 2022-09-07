package org.slams.server.chat.service;

import lombok.RequiredArgsConstructor;
import org.slams.server.chat.convertor.ChatroomMappingConvertor;
import org.slams.server.chat.dto.common.ChatroomType;
import org.slams.server.chat.dto.response.ChatroomResponse;
import org.slams.server.chat.dto.response.ResultOfCreatingOfChatroomResponse;
import org.slams.server.chat.dto.response.ResultOfCreatingOfCourtChatroomResponse;
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
    public ResultOfCreatingOfChatroomResponse saveUserChatroom(Long userId, Long chatroomId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 사용자는 존재하지 않습니다."));

        return ResultOfCreatingOfChatroomResponse.builder()
                .chatroomId("SIDOFIDLIFDJL")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    public ResultOfCreatingOfCourtChatroomResponse saveUserCourtChatroom(Long userId, Long courtId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 사용자는 존재하지 않습니다."));
        CourtChatroomMapping courtChatroomMapping = courtChatroomMappingRepository.findByCourtId(courtId);
        return chatroomMappingConvertor.toDto(
                userChatRoomMappingRepository.save(
                UserChatroomMapping.of(user, courtChatroomMapping, courtId)
            )
        );
    }

    public List<ChatroomResponse> findUserChatroomListByUserId(Long userId, CursorPageRequest cursorRequest){
        return List.of(
                ChatroomResponse.builder()
                        .chatroomId("LIDSLDIJFL")
                        .chatroomName("")
                        .chatroomType(ChatroomType.PERSONAL)
                        .lastChat("마지막 채팅 내용")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
    }

    public void deleteUserChatroomByCourtId(Long courtId, Long userId){
            userChatRoomMappingRepository.deleteUserChatRoomByCourtId(courtId, userId);
    }

    public List<UserChatroomMapping> cursorPageForFindAllByUserId(Long userId, CursorPageRequest cursorRequest){
        PageRequest pageable = PageRequest.of(0, cursorRequest.getSize());
        return cursorRequest.getIsFirst() ?
                userChatRoomMappingRepository.findAllByUserIdByCreated(userId, pageable):
                userChatRoomMappingRepository.findAllByUserIdMoreThenLastIdByCreated(userId, cursorRequest.getLastIdParedForLong(), pageable);
    }

    public Long findLastId(Long userId, CursorPageRequest cursorRequest){
        PageRequest pageable = PageRequest.of(0, cursorRequest.getSize());
        List<Long> ids = cursorRequest.getIsFirst() ?
                userChatRoomMappingRepository.findIdByUserIdByCreated(userId, pageable):
                userChatRoomMappingRepository.findIdByUserIdMoreThenLastIdByCreated(userId, cursorRequest.getLastIdParedForLong(), pageable);

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
                return ids.get(ids.size() - 1);
            }

        }

    }
}
