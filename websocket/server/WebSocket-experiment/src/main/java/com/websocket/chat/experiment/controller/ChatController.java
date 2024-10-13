package com.websocket.chat.experiment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.chat.experiment.dto.CreateConversationRequest;
import com.websocket.chat.experiment.dto.CreateUserRequest;
import com.websocket.chat.experiment.entity.Conversation;
import com.websocket.chat.experiment.entity.Message;
import com.websocket.chat.experiment.entity.User;
import com.websocket.chat.experiment.service.ChatServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/chat/")
public class ChatController {

    private final ChatServices services;

    public ChatController(ChatServices services) {
        this.services = services;
    }

    @PostMapping("new-user")
    public ResponseEntity<User> newUser(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.newUser(userRequest));
    }

    @PostMapping("new-conversation")
    public ResponseEntity<Conversation> newConversation(@RequestBody CreateConversationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.newConversation(request));
    }

    @GetMapping("/conversation-by-id")
    public Conversation findById(@RequestParam Long id) {
        return services.findById(id);
    }

    @GetMapping("/user-by-id")
    public User getUserById(@RequestParam Long id) {
        return services.getUserById(id);
    }

    @PostMapping("path")
    public void saveMessage(@RequestBody Message message) {        
        services.saveMessage(message);
    }
    

}
