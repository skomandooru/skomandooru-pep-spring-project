package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Map<String, Object> createMessage(Message newMessage) {
        Map<String, Object> response = new HashMap<>();
        if (newMessage.getMessageText().isEmpty() || ((CharSequence) newMessage.getMessageText()).length() > 255) {
            response.put("status", 400);
            return response;
        }

        Optional<Account> account = accountRepository.findById(newMessage.getPostedBy());
        if (!account.isPresent()) {
            response.put("status", 400);
            return response;
        }

        newMessage.setTime_posted_epoch(System.currentTimeMillis());
        Message createdMessage = messageRepository.save(newMessage);
        response.put("status", 200);
        response.put("message", createdMessage);
        return response;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        } else {
            try {
                throw new Exception("Message not found");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, Object> deleteMessage(Long messageId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.delete(message.get());
        }
        response.put("status", 200);
        return response;
    }

    public Map<String, Object> updateMessageText(Long messageId, String newMessageText) {
        Map<String, Object> response = new HashMap<>();
        if (newMessageText.isEmpty() || newMessageText.length() > 255) {
            response.put("status", 400);
            return response;
        }

        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            message.get().setMessage_id(newMessageText);
            messageRepository.save(message.get());
            response.put("status", 200);
        } else {
            response.put("status", 400);
        }
        return response;
    }

    public List<Message> getMessagesByUser(Long accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    // Add other message-related methods here
}
