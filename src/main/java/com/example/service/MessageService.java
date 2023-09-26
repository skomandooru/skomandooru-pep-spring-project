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

    public Message updateMessage (Message message) throws Exception {
    
    if(message.getMessage_text()==null || message.getMessage_text().equals("")){
        throw new Exception("message text is empty");
    } 

    if(message.getMessage_text().length()>255) 
        throw new Exception("too many characters");
  
    if(!messageRepository.existsById(message.getMessage_id()))
        throw new Exception("message does not exist");

    return messageRepository.save(message);
    } 

    public List <Message> getAllMessages () {
        return messageRepository.findAll();
    }

    /*if(Message.getMessageByMessageId()==null){
    try {
        throw new InvalidMessageException("message text does not exist");
    } catch (InvalidMessageException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }  
    }     
}*/
    public Message retrieveMessagebyid (Integer messageId) throws Exception{
        if(!messageRepository.existsById(messageId))
        throw new Exception("message does not exist");    

        return messageRepository.findById(messageId).get();
    }

    public Message createmessage(Message message) throws Exception {
        if(message.getMessage_text()==null || message.getMessage_text().equals("")){
            throw new Exception("message text is empty");
        
        } 
    
        if(message.getMessage_text().length()>255) 
            throw new Exception("too many characters");
      
        /*if(!messageRepository.existsById(message.getMessage_id()))
            throw new InvalidMessageException("message does not exist");*/

        return messageRepository.save(message);
}

    public int getMessage_id(Long messageId, String string) {
        return 0;
    }

    public List<Message> getMessagesByUser(Long accountId) {
        return null;
    }

    public void deleteMessage(Long messageId) {
    }

    public Message retrieveMessagebyid(Long messageId) {
        return null;
    }   }
