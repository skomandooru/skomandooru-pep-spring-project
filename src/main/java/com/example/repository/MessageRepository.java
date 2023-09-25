package com.example.repository;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;

public interface MessageRepository {

    Message save(Message newMessage);

    List<Message> findAll();

    Optional<Message> findById(Long messageId);

    void delete(Message message);

    List<Message> findByPostedBy(Long accountId);
}
