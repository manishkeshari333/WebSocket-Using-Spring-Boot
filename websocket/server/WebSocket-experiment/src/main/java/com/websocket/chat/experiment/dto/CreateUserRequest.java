package com.websocket.chat.experiment.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String userName;

    private String userEmail;

}
