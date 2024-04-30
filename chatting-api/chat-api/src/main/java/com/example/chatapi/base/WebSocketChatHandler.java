package com.example.chatapi.base;

import com.example.chatapi.chat.application.ChatService;
import com.example.chatapi.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();

        ChatMessage chatMessage = Util.Chat.resolvePayload(payload);

        chatService.handleAction(chatMessage.getRoomId(), session, chatMessage);
    }
}
