package com.example.socket.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 웹 소켓 메시지를 다룰 수 있게 허용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 접두사 등록 선언
    @Override
    public void configureMessageBroker (MessageBrokerRegistry registry) {
        // 발행자가 "/sub"의 경로로 메시지를 주면 구독자들에게 전달
        registry.enableSimpleBroker("/api/sub");

        // 발행자가 "/pub"의 경로로 메시지를 주면 가공 후 구독자들에게 전달
        // /pub/... 경로로 요청을 보내면 @MessageMapping으로 향한다.
        registry.setApplicationDestinationPrefixes("/api/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 커넥션을 맺는 경로 설정.
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("**");
    }

}
