package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
       
       if(accountService.checkUsernameAvailability(newAccount.getUsername() && accountService.login(newAccount.getUsername(), null), newAccount.getPassword())) {
            accountService.addAccount(newAccount);
            return new ResponseEntity<Account>(accountService.loginAccount(newAccount), HttpStatus.OK);
       }
       else if(!accountService.checkUsernameAvailability(newAccount.getUsername())) {
        return new ResponseEntity<Account>(HttpStatus.CONFLICT);
       }       
       return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }   

    // Endpoint 2: User Login
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> login(@RequestBody Map<String, String> loginRequest) {
        try {
            Account account = accountService.login(loginRequest.get("username"), loginRequest.get("password"));
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
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
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Endpoint 5: Retrieve a message by its ID
    @GetMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Long messageId) {
        try {
            Message message = messageService.retrieveMessagebyid(messageId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // Endpoint 6: Delete a message by its ID
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("message_id") Long messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.OK); // Deleting a non-existent message is considered successful
        }
    }

    // Endpoint 7: Update a message text by its ID
    @PatchMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Integer> updateMessageText(
            @PathVariable("message_id") Long messageId,
            @RequestBody Map<String, String> updateRequest) {
        try {
            int rowsUpdated = messageService.getMessage_id(messageId, updateRequest.get("message_text"));
            return ResponseEntity.ok(rowsUpdated);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    // Endpoint 8: Retrieve all messages by a particular user
    @GetMapping("/accounts/{account_id}/messages")
    @ResponseBody
    public List<Message> getMessagesByUser(@PathVariable("account_id") Long accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}
