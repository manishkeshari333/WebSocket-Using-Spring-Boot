package com.websocket.chat.experiment.service;

import com.websocket.chat.experiment.dto.CreateConversationRequest;
import com.websocket.chat.experiment.dto.CreateUserRequest;
import com.websocket.chat.experiment.entity.Conversation;
import com.websocket.chat.experiment.entity.Message;
import com.websocket.chat.experiment.entity.User;

public interface ChatServices {

    Conversation newConversation(CreateConversationRequest request);

    User newUser(CreateUserRequest userRequest);

    Conversation findById(Long id);

    User getUserById(Long id);

    void saveMessage(Message message);

}
