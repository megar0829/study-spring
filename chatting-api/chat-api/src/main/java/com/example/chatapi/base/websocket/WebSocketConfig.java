package com.example.chatapi.base;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker // 웹 소켓 서버를 사용 선언
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // 웹소켓 관련 기능

    // 메시지를 전송할 때 /sub 으로 보내면 /pub 으로 시작되는 주제를 가진 메시지 핸들러로 라우팅하여
    // 해당 주제를 구독하고 있는 모든 클라이언트에게 메시지를 전달
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // 웹소켓에 접속하는 endPoint 등록
        registry.addEndpoint("/ws-stomp")
                .withSockJS();
    }
}
