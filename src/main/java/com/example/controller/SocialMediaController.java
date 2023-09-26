package com.example.controller;

import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    /**
     * @param account
     * @return
     */
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public AccountService registerUser(Account account) {
        if(account.getUsername().contains(account.getUsername())) {
            return AccountService.status(409).body(accountService.addAccount(account)); 
        }
        else if (account.getUsername().isBlank() || (account.getPassword().length() <= 4)) {
            return AccountService.status(400). body(accountService.addAccount(account));
        }
        else {
            return  AccountService.status(200).body(accountService.addAccount(account));
        }
        return accountService;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> loginUser(@RequestBody Map<String, String> loginRequest) {
        return accountService.login(loginRequest.get("username"), loginRequest.get("password"));
    }

    @PostMapping("/messages")
    @ResponseBody
    public Map<String, Object> createMessage(@RequestBody Message newMessage) {
        return Message.createMessage(newMessage);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    @ResponseBody
    public Message getMessageById(@PathVariable("message_id") Long messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{message_id}")
    @ResponseBody
    public Map<String, Object> deleteMessage(@PathVariable("message_id") Long messageId) {
        return messageService.deleteMessage(messageId);
    }

    @PatchMapping("/messages/{message_id}")
    @ResponseBody
    public Map<String, Object> updateMessageText(
            @PathVariable("message_id") Long messageId,
            @RequestBody Map<String, String> updateRequest) {
        return messageService.updateMessageText(messageId, updateRequest.get("message_text"));
    }

    @GetMapping("/accounts/{account_id}/messages")
    @ResponseBody
    public List<Message> getMessagesByUser(@PathVariable("account_id") Long accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}
