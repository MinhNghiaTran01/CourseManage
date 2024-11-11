package com.javaweb.course.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        tiền tố topic để gửi thông điệp từ server về client
        config.enableSimpleBroker("/topic");
//        tiền tố app để client gui đến server
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Tạo endpoint cho client kết nối
                .setAllowedOriginPatterns("*") // Cho phép tất cả các nguồn truy cập
                .withSockJS(); // Sử dụng SockJS để hỗ trợ fallback cho WebSocket
    }

}