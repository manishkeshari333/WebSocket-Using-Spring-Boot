package com.websocket.chat.experiment.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;

    private String userEmail;

    private Long userOtp;
    
    private String otpValidationTime;

    // Many-to-Many relationship with conversations
    @ManyToMany(mappedBy = "users")
    private List<Conversation> conversations;
}
