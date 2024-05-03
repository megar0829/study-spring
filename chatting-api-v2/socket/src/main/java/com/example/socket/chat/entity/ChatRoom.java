package com.example.socket.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    public static ChatRoom createRoom(String name) {
        ChatRoom room = new ChatRoom();
        room.id = UUID.randomUUID();
        room.name = name;
        return room;
    }
}
