package com.websocket.chat.experiment.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    // Store all sessions
    private List<WebSocketSession> sessions = new ArrayList<>();

    @SuppressWarnings("null")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Store the session when WebSocket connection is established
        sessions.add(session);
        log.info("New session established: " + session.getId());
    }

    @SuppressWarnings("null")
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming WebSocket message
        String userMessage = message.getPayload();
        // Broadcast the received message to all connected clients
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(userMessage));
            log.info(userMessage);
        }
    }

    @SuppressWarnings("null")
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove session when WebSocket connection is closed
        sessions.remove(session);
        log.info("Session closed: " + session.getId());
    }
}
