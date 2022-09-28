package org.slams.server.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slams.server.chat.dto.request.CreateChatroomRequest;
import org.slams.server.chat.dto.response.*;
import org.slams.server.chat.service.ChatService;
import org.slams.server.chat.service.ChatroomService;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.apiTest.CursorPageResponse;
import org.slams.server.common.api.TokenGetId;
import org.slams.server.common.apiTest.DefaultApiResponse;
import org.slams.server.common.apiTest.MetaReference;
import org.slams.server.common.apiTest.Reference;
import org.slams.server.common.dto.Court;
import org.slams.server.common.dto.Loudspeaker;
import org.slams.server.common.dto.User;
import org.slams.server.common.dto.referenceDto.UserResponse;
import org.slams.server.common.dto.referenceDto.UserAndLoudspeakerResponse;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunyun on 2021/12/18.
 */

@Api(tags = "채팅 기능")
@RestController
@RequestMapping(value = "/api/v1/chat", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ChatController {

    private final ChatroomService chatroomService;
    private final ChatService chatContentsService;
    private final Jwt jwt;

    /*
     * swagger v3 openapi 사용 중
     * 구글 검색 시 openapi 3.0 인지 swagger 인지 확인할 필요가 있음
     * @ 사용 시 import io.swagger.v3.oas 사용해야한다
     * @ApiResponses
     * https://www.baeldung.com/java-swagger-set-list-response
     */

    @Operation(summary = "[DEFAULT] 채팅방 생성", description = "채팅방 생성, metaOfReference={user}")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description  = "생성 성공")
    })
    @PostMapping("/chatroom")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DefaultApiResponse<ResultOfCreatingOfChatroomResponse>> createChatroom(
            HttpServletRequest request,
            @RequestBody CreateChatroomRequest createChatroomRequest
            ){
        Long creatorId = new TokenGetId(request,jwt).getUserId();
        MetaReference referenceMap = new MetaReference();
        Reference<User> userReference = new Reference<>();
        referenceMap.put("user", userReference.createReference(List.of(null)));
        return ResponseEntity.ok(
                new DefaultApiResponse<>(
                        ResultOfCreatingOfChatroomResponse.builder()
                            .id("CHATROOM_ID")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                        referenceMap
                )
        );
    }

    @Operation(summary = "채팅방 리스트 조회", description = "user id에 의해 사용자가 참여하고 있는 채팅방 리스트 조희, metaOfReference={user}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description  = "조회 성공")
    })
    @GetMapping("/chatroom")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CursorPageResponse<List<ChatroomResponse>>> findChatroomListByUserId(
            CursorPageRequest cursorRequest,
            HttpServletRequest request
    ){
        Long userId = new TokenGetId(request,jwt).getUserId();
        
        MetaReference referenceMap = new MetaReference();
        Reference<User> userReference = new Reference<>();
        referenceMap.put("user", userReference.createReference(List.of(null)));
        return ResponseEntity.ok(new CursorPageResponse<>(
                chatroomService.findUserChatroomListByUserId(userId, cursorRequest),
                referenceMap,
                String.valueOf(chatroomService.findLastId(userId.toString(), cursorRequest))
                )
        );
    }

    @Operation(summary = "[DEFAULT] 채팅 리스트", description = "chatroom id 에 의해 채팅방의 채팅 내용 조회, metaOfReference={user, loudspeaker}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description  = "조회 성공")
    })
    @GetMapping("/chatroom/{chatroomId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CursorPageResponse<List<ChatOfChatroomResponse>>> findChatListByChatroomId(
            @PathVariable String chatroomId,
            CursorPageRequest cursorRequest,
            HttpServletRequest request
    ){
        MetaReference referenceMap = new MetaReference();
        Reference<User> userReference = new Reference<>();
        Reference<Loudspeaker> loudspeakerReference = new Reference<>();
        referenceMap.put("user", userReference.createReference(List.of(null)));
        referenceMap.put("loudspeaker", loudspeakerReference.createReference(List.of(null)));
        return ResponseEntity.ok(new CursorPageResponse<>(
                        chatContentsService.findChatContentsListByCourtOrderByCreatedAt(chatroomId, cursorRequest),
                        referenceMap,
                        String.valueOf(chatContentsService.findLastId(chatroomId, cursorRequest))
                )
        );
    }

    @Operation(summary = "[DEFAULT] 사용자의 참여 채팅방 생성", description = "chatroomId id에 의해, 사용자가 참여하고 있는 채팅 리스트에서 본 채팅방 생성, metaOfReference={user, court}")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description  = "생성 성공")
    })
    @PostMapping("/chatroom/{chatroomId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DefaultApiResponse<ResultOfCreatingOfChatroomToParticipateInResponse>> createChatroomByUserId(
            @PathVariable String chatroomId,
            HttpServletRequest request
    ){
        MetaReference referenceMap = new MetaReference();
        Reference<User> userReference = new Reference<>();
        Reference<Court> courtReference = new Reference<>();
        referenceMap.put("user", userReference.createReference(List.of(null)));
        referenceMap.put("court",courtReference.createReference(List.of(null)));
        Long userId = new TokenGetId(request,jwt).getUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DefaultApiResponse<>(
                        chatroomService.saveUserChatroom(userId, chatroomId),
                        referenceMap
                        )
                );
    }

    @Operation(summary = "[DEFAULT] 사용자의 참여 채팅방 삭제", description = "chatroomId id에 의해, 사용자가 참여하고 있는 채팅 리스트에서 본 채팅방을 삭제, metaOfReference={}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description  = "삭제 성공")
    })
    @DeleteMapping("/chatroom")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<DefaultApiResponse<ResultOfDeletingUserChatroomResponse>> deleteChatroomByUserIdAndChatroomId(
            @Parameter(name = "삭제할 채팅 구별키 리스트", required = true, example = "[chatroom_id, chatroom_id]", description = "예시 -> [chatroom_id, chatroom_id]")
            @RequestParam List<String> chatroomIds,
            HttpServletRequest request
    ){
        Long userId = new TokenGetId(request,jwt).getUserId();
        return ResponseEntity.ok(
                new DefaultApiResponse<>(
                        ResultOfDeletingUserChatroomResponse.builder()
                                .ids(chatroomIds)
                                .build(),
                        null
                )
        );
    }



    @Operation(summary = "[COURT] 채팅 리스트", description = "court id에 의해 COURT 채팅방의 채팅 내용 조회, metaOfReference={user, loudspeaker}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description  = "조회 성공")
    })
    @GetMapping("/court/{courtId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CursorPageResponse<List<ChatOfChatroomResponse>>> findChatByCourtId(
            @PathVariable String courtId,
            CursorPageRequest cursorRequest,
            HttpServletRequest request
    ){
        MetaReference referenceMap = new MetaReference();
        Reference<User> userReference = new Reference<>();
        Reference<Loudspeaker> loudspeakerReference = new Reference<>();
        referenceMap.put("user", userReference.createReference(List.of(null)));
        referenceMap.put("loudspeaker", loudspeakerReference.createReference(List.of(null)));

        return ResponseEntity.ok(new CursorPageResponse<>(
               chatContentsService.findChatContentsListByCourtOrderByCreatedAt(courtId, cursorRequest),
                referenceMap,
                String.valueOf(chatContentsService.findLastId(courtId, cursorRequest))
                )
        );
    }


    @Operation(summary = "[COURT] 사용자의 참여 채팅방 생성", description = "court id에 의해, 사용자가 참여하고 있는 COURT 채팅 리스트에 채팅방을 생성, metaOfReference={court}")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description  = "생성 성공")
    })
    @PostMapping("/court/{courtId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DefaultApiResponse<ResultOfCreatingOfCourtChatroomToParticipateInResponse>> createChatroomByUserIdAndCourtId(
            @PathVariable String courtId,
            HttpServletRequest request
    ){
        Long userId = new TokenGetId(request,jwt).getUserId();

        MetaReference referenceMap = new MetaReference();
        Reference<Court> courtReference = new Reference<>();
        referenceMap.put("court", courtReference.createReference(List.of(null)));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new DefaultApiResponse<>(
                                chatroomService.saveUserCourtChatroom(userId, courtId),
                                referenceMap
                        )
                );
    }

    @Operation(summary = "[COURT] 사용자의 참여 채팅방 삭제", description = "court id에 의해, 사용자가 참여하고 있는 COURT 채팅 리스트에서 본 채팅방을 삭제, metaOfReference={}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description  = "삭제 성공")
    })
    @DeleteMapping("/court/{courtId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<DefaultApiResponse<ResultOfDeletingOfUserCourtChatroomResponse>> deleteCourtChatroomByUserIdAndCourtId(
            @PathVariable String courtId,
            HttpServletRequest request
            ){
        Long userId = new TokenGetId(request,jwt).getUserId();
        chatroomService.deleteUserChatroomByCourtId(courtId, userId);

        return ResponseEntity.ok(
                new DefaultApiResponse<>(
                        ResultOfDeletingOfUserCourtChatroomResponse.builder()
                                .courtId(null)
                                .chatroomId(null)
                                .build(),
                        null
                )
        );
    }


}
