package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired 
    private MessageRepository messageRepository;

    public Message getAllMessages(String message) {
        return messageRepository.getAllMessages(message);
    }
    
    public Message createmessage(Message message) throws Exception {
        if(message.getMessage_text()==null || message.getMessage_text().equals("")){
            throw new Exception("message text is empty");
        
        } 
    
        if(message.getMessage_text().length()>255) 
            throw new Exception("too many characters");
      
        if(!messageRepository.existsById(message.getMessage_id()))
            throw new Exception("message does not exist");

        return messageRepository.save(message);
}

    public int getMessage_id(Long messageId, String string) {
        return 0;
    }

    public List<Message> getMessagesByUser(Long accountId) {
        return null;
    }

    public int deleteMessageById(int message_id) {
        if (doesMessageExist(message_id)) {
            messageRepository.deleteById(message_id);
            return 1; 
        } else {
            return 0; 
        }
    }

    public boolean doesMessageExist(int messageId) {
        return messageRepository.existsById(messageId);
    }

    public boolean checkMessageAvailability(String message) {
        if (message != "" && message.length() <= 255) {
            return true;
        }
        return false;
    } 

    public Integer updateMessageById(int messageId, String request) {
        return messageRepository.updateMessage(messageId, request);
    }

    public Message getMessageById(int messageId) {
        return messageRepository.getMessageById(messageId);
    }
    
    public Message retrieveById(int messageId) {
        //int messageI = messageId.getMessage_id();
        return messageRepository.findById(messageId).orElse(null);
    } 
}
