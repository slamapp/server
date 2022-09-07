package org.slams.server.chat.controller;

import org.slams.server.chat.dto.request.CreateChatOfCourtChatroomRequest;
import org.slams.server.chat.dto.response.ChatOfChatroomResponse;
import org.slams.server.chat.service.ChatService;
import org.slams.server.common.utils.WebsocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by yunyun on 2021/12/18.
 */

@Controller
public class ChatWebSocketController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WebsocketUtil websocketUtil;
    private final ChatService chatContentsService;
    private final SimpMessagingTemplate websocket;

    public ChatWebSocketController(
            WebsocketUtil websocketUtil,
            ChatService chatContentsService,
            SimpMessagingTemplate websocket){

        this.websocketUtil = websocketUtil;
        this.chatContentsService = chatContentsService;
        this.websocket = websocket;
    }

//    @MessageMapping("/chat")
    public void chat(CreateChatOfCourtChatroomRequest request, SimpMessageHeaderAccessor headerAccessor){
        Long userId = websocketUtil.findTokenFromHeader(headerAccessor);

        ChatOfChatroomResponse chatContentsResponse = chatContentsService.saveChatConversationContent(request, userId);

        websocket.convertAndSend(
                String.format("/user/%d/chat", request.getCourtId()),
                chatContentsResponse
        );
    }

}
