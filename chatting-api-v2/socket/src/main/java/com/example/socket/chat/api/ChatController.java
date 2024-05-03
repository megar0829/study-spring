package com.example.socket.chat.api;

import com.example.socket.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * MessageMapping annotation은 메시지의 destination이 /hello였다면 해당 sendMessage() method가 불리도록 해준다.
     * sendMessage()에서는 simpMessagingTemplate.convertAndSend를 통해 /sub/chat/{channelId} 채널을 구독 중인 클라이언트에게 메시지를 전송한다.
     * SimpMessagingTemplate 는 특정 브로커로 메시지를 전달한다.
     * 현재는 외부 브로커를 붙이지 않았으므로 인메모리에 정보를 저장한다.
     * 메시지의 payload는 인자(chatDto)로 들어온다.
     */
    @MessageMapping("/chat")
    public void sendMessage(Message message, SimpMessageHeaderAccessor accessor) {
        simpMessagingTemplate.convertAndSend("/sub/chat" + message.getChatRoomId(), message);
    }
}
