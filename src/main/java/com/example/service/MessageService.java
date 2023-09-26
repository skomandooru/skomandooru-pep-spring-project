package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message newMessage) {
        if (newMessage.getMessageText().isEmpty() || ((CharSequence) newMessage.getMessageText()).length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }

        Optional<Account> account = AccountRepository.findById(newMessage.getPostedBy());
        if (!account.isPresent()) {
            throw new IllegalArgumentException("Invalid user");
        }

        newMessage.setTime_posted_epoch(System.currentTimeMillis());
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long messageId) throws Exception {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        } else {
            throw new Exception("Message not found");
        }
    }

    public void deleteMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.delete(message.get());
        } // No need to throw an exception if the message does not exist
    }

    public int updateMessageText(Long messageId, String newMessageText) throws Exception {
        if (newMessageText.isEmpty() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }

        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            message.get().setMessage_text(newMessageText);
            messageRepository.save(message.get());
            return 1; // Rows updated
        } else {
            throw new Exception("Message not found");
        }
    }

    public List<Message> getMessagesByUser(Long accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
    
    // Add other message-related methods here
}
