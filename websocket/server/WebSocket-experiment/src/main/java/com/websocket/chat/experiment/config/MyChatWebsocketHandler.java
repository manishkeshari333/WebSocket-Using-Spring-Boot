package com.websocket.chat.experiment.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.experiment.entity.Conversation;
import com.websocket.chat.experiment.entity.Message;
import com.websocket.chat.experiment.entity.User;
import com.websocket.chat.experiment.repo.ConversationRepo;
import com.websocket.chat.experiment.repo.MessageRepo;
import com.websocket.chat.experiment.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyChatWebsocketHandler extends TextWebSocketHandler {

    // Store all active sessions
    private List<WebSocketSession> sessions = new ArrayList<>();

    // ObjectMapper for parsing incoming JSON messages
    private ObjectMapper objectMapper = new ObjectMapper();

    private final ConversationRepo conversationRepo;
    private final MessageRepo messageRepo;
    private final UserRepo userRepo;

    public MyChatWebsocketHandler(ConversationRepo conversationRepo, MessageRepo messageRepo, UserRepo userRepo) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @SuppressWarnings("null")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("New session established: " + session.getId());

    }

    @SuppressWarnings({ "null", "unchecked" })
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // Parse the incoming JSON message
        Map<String, Object> messageData = objectMapper.readValue(message.getPayload(), Map.class);
        Long userId = Long.parseLong(messageData.get("userId").toString());
        Long conversationId = Long.parseLong(messageData.get("conversationId").toString());
        String payload = messageData.get("payload").toString();

        // Fetch the User and Conversation from the database
        Optional<User> userById = userRepo.findById(userId);
        User user = userById.get();
        Optional<Conversation> ConversationById = conversationRepo.findById(conversationId);
        Conversation conversation = ConversationById.get();

        // Create a new Message entity
        Message newMessage = new Message();
        newMessage.setConversation(conversation);
        newMessage.setPayload(payload);
        newMessage.setRead(false);

        // Save the message to the database
        messageRepo.save(newMessage);
        
        // Broadcast the received message to all participants of the conversation
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("User " + user.getUserName() + ": " + payload));
                log.info("Message from User " + user.getUserName() + ": " + payload);
            }
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
