package com.example.chatapi.chat.dao;

import com.example.chatapi.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        redisTemplate.delete(CHAT_ROOMS);
        for (int i = 0; i < 5; i++) {
            ChatRoom chatRoom = ChatRoom.of("test_" + i);
            opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        }
    }

    public List<ChatRoom> findAll() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    public void save(ChatRoom chatRoom) {
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
    }
}