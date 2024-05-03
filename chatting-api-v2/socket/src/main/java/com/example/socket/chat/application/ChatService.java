package com.example.socket.chat.application;

import com.example.socket.chat.entity.ChatRoom;

import java.util.List;

public interface ChatService {
    void init();

    List<ChatRoom> findAllRoom();

    ChatRoom findById(String roomId);

    ChatRoom createRoom(String name);


}
