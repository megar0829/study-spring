package com.example.chatapi.chat.application;

import com.example.chatapi.base.Util;
import com.example.chatapi.chat.dao.ChatRepository;
import com.example.chatapi.chat.dto.ChatMessage;
import com.example.chatapi.chat.dto.ChatRoom;
import com.example.chatapi.chat.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public List<ChatRoom> findByAll() {
        return chatRepository.findByAll();
    }

    @Override
    public ChatRoom findRoomById(String roomId) {
        return chatRepository.findById(roomId);
    }

    @Override
    public ChatRoom createRoom(String name) {
        String roomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.of(roomId, name);
        chatRepository.save(roomId, chatRoom);

        return chatRoom;
    }

    @Override
    public void handleAction(String roomId, WebSocketSession session, ChatMessage chatMessage) {
        ChatRoom room = chatRepository.findById(roomId);

        if (inEnterRoom(chatMessage)) {
            room.join(session);
            chatMessage.setMessage(chatMessage.getSender() + "님 환영합니다.");
        }

        TextMessage textMessage = Util.Chat.resolveTextMessage(chatMessage);
        room.sendMessage(textMessage);
    }

    private boolean inEnterRoom(ChatMessage chatMessage) {
        return chatMessage.getMessageType().equals(MessageType.ENTER);
    }
}
