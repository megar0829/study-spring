package com.example.chatapi.chat.application;

import com.example.chatapi.chat.dto.ChatMessage;
import com.example.chatapi.chat.dto.ChatRoom;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatService {

    List<ChatRoom> findByAll();

    ChatRoom findRoomById(String roomId);

    ChatRoom createRoom(String name);

    void handleAction(String roomId, WebSocketSession session, ChatMessage chatMessage);
}
