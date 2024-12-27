package com.databaseProject.backend.configuration;

import com.databaseProject.backend.handler.NotificationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler(), "/notifications")
                .setAllowedOrigins("*");
    }

    @Bean
    public NotificationHandler notificationHandler() {
        return new NotificationHandler(sessions(), objectMapper());
    }

    @Bean
    public Map<Integer, WebSocketSession> sessions() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
