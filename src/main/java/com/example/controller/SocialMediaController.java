package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api")
public class SocialMediaController {

    //private final AtomicLong accountIdCounter = new AtomicLong(1);
    //private final AtomicLong messageIdCounter = new AtomicLong(1);
    //private final List<Account> accounts = new ArrayList<>();
    //private final List<Message> messages = new ArrayList<>();

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // Endpoint 1: User Registration
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {
       
        if(accountService.checkUsernameAvailability(newAccount.getUsername()) && accountService.validAccount(newAccount.getUsername(), newAccount.getPassword())) {
            accountService.addAccount(newAccount);
            return new ResponseEntity<Account>(accountService.loginAccount(newAccount), HttpStatus.OK);
       }
       else if(!accountService.checkUsernameAvailability(newAccount.getUsername())) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
       }       
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }   

    // Endpoint 2: User Login
    @PostMapping("/login")
    //@ResponseBody
    public ResponseEntity<?> login(@RequestBody Account newAccount) {
            if (accountService.loginAccount(newAccount)!= null) {
                return new ResponseEntity<Account>(accountService.loginAccount(newAccount), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
    }

    // Endpoint 3: Create a new message
    @PostMapping("/messages")
    public ResponseEntity<?> postMessageHandler(@RequestBody Message message){
        try {
            Message createmessage = messageService.createmessage(message);            
            return ResponseEntity.ok(createmessage);
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    // Endpoint 4: Retrieve all messages
    @GetMapping("/messages")
    @ResponseBody
    public ArrayList<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Endpoint 5: Retrieve a message by its ID
    @GetMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") int messageId) {
        
        Message message = messageService.retrieveById(messageId);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
        
        // if (messageService.retrieveById(messageId)!= null) {
        //     return new ResponseEntity<>(HttpStatus.OK);
        // }
        // else {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
    }

    // Endpoint 6: Delete a message by its ID
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") int messageId) {
        int rowsEffected = messageService.deleteMessageById(messageId);
        return new ResponseEntity<>(rowsEffected, HttpStatus.OK);
    } 
    
    // Endpoint 7: Update a message text by its ID
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("message_id") int messageId, @RequestBody Message request) throws Exception {
      /*   Message existingMessage = messageService.getMessageById(messageId);
        String newMessageText = request.getMessage_text();
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255 || existingMessage == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        existingMessage.setMessage_text(newMessageText);
        messageService.createmessage(existingMessage);
        return new ResponseEntity<>(1, HttpStatus.OK); 
        */
        if (messageService.getMessageById(messageId)!= null && messageService.checkMessageAvailability(request.getMessage_text())) {
            //messageService.updateMessage(messageId, request.getMessageText());
            return new ResponseEntity<Integer>(messageService.updateMessageById(messageId, request.getMessage_text()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
    }
  
    // Endpoint 8: Retrieve all messages by a particular user
    @GetMapping("/accounts/{account_id}/messages")
    @ResponseBody
    public List<Message> getMessagesByUser(@PathVariable("account_id") Long accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}
