package com.websocket.chat.experiment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MyChatWebsocketHandler chatWebsocketHandler;

    @Autowired
    public WebSocketConfig(MyChatWebsocketHandler chatWebsocketHandler) {
        this.chatWebsocketHandler = chatWebsocketHandler;
    }

    @SuppressWarnings("null")
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebsocketHandler, "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    // @Override
    // public void registerWebSocketHandlers(@SuppressWarnings("null")
    // WebSocketHandlerRegistry registry) {
    // // Add WebSocket handler for "/chat" endpoint
    // registry.addHandler(new MyWebSocketHandler(), "/chat")
    // .setAllowedOrigins("*"); // "*" means any origin is allowed
    // }

}
