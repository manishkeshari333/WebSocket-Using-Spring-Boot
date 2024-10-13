package com.websocket.chat.experiment.dto;

import lombok.Data;

@Data
public class CreateConversationRequest {

    private String conversationName;
    private Long createrId;

}
