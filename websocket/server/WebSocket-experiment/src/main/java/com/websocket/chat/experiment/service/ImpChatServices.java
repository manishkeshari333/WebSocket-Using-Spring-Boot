package com.websocket.chat.experiment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.websocket.chat.experiment.dto.CreateConversationRequest;
import com.websocket.chat.experiment.dto.CreateUserRequest;
import com.websocket.chat.experiment.entity.Conversation;
import com.websocket.chat.experiment.entity.Message;
import com.websocket.chat.experiment.entity.User;
import com.websocket.chat.experiment.repo.ConversationRepo;
import com.websocket.chat.experiment.repo.MessageRepo;
import com.websocket.chat.experiment.repo.UserRepo;

@Service
public class ImpChatServices implements ChatServices {

    private final ConversationRepo conversationRepo;
    private final MessageRepo messageRepo;
    private final UserRepo userRepo;

    public ImpChatServices(ConversationRepo conversationRepo, MessageRepo messageRepo, UserRepo userRepo) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @Override
    public User newUser(CreateUserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setUserEmail(userRequest.getUserEmail());
        return userRepo.save(user);
    }

    @Override
    public Conversation newConversation(CreateConversationRequest request) {
        Conversation conv = new Conversation();
        conv.setConversationName(request.getConversationName());
        List<User> listOfUser = getUser(request);
        conv.setUsers(listOfUser);
        return conversationRepo.save(conv);
    }

    @Override
    public Conversation findById(Long id) {
        Optional<Conversation> byId = conversationRepo.findById(id);
        return byId.get();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> byId = userRepo.findById(id);
        return byId.get();
    }

    @Override
    public void saveMessage(Message message) {
        Message saveMessage = new Message();
        saveMessage.setConversation(message.getConversation());
        saveMessage.setPayload(message.getPayload());
        saveMessage.setRead(message.isRead());
        messageRepo.save(saveMessage);
    }

    private List<User> getUser(CreateConversationRequest request) {
        List<User> listOfUser = new ArrayList<>();
        User user = new User();
        user.setUserId(request.getCreaterId());
        listOfUser.add(user);
        return listOfUser;
    }

}
