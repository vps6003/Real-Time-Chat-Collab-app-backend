package com.chatapp.app_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.chatapp.app_backend.websocket.SignalingSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SignalingSocketHandler signalingSocketHandler;

    // read from application.properties
    @Value("${app.ws.endpoint}")
    private String wsEndpoint;

    public WebSocketConfig(SignalingSocketHandler signalingSocketHandler) {
        this.signalingSocketHandler = signalingSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(signalingSocketHandler, wsEndpoint)
                .setAllowedOrigins("*"); // dev mode only

//        // print in IntelliJ terminal ( also have to import logger so won't log here)
//        logger.info("🚀 WebSocket endpoint registered at: ws://localhost:8080" + wsEndpoint);
    }
}
