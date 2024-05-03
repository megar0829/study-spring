package com.example.socket.chat.application;

import com.example.socket.chat.entity.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private Map<String, ChatRoom> chatRooms;

    @Override
    @PostConstruct
    public void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 채팅방 리스트 조회
    @Override
    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    @Override
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    @Override
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.createRoom(name);
        chatRooms.put(chatRoom.getId().toString(), chatRoom);

        return chatRoom;
    }
}
