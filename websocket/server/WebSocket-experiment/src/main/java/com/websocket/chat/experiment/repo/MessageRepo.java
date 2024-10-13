package com.websocket.chat.experiment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.websocket.chat.experiment.entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {

}
