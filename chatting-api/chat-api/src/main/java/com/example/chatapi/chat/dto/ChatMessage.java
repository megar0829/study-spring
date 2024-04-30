package com.example.chatapi.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private MessageType messageType;

    private String roomId;

    private String sender;

    private String message;
}
