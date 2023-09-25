package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Map<String, Object> registerAccount(Account newAccount) {
        Map<String, Object> response = new HashMap<>();
        if (newAccount.getUsername().isEmpty() || newAccount.getPassword().length() < 4) {
            response.put("status", 400);
            return response;
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        if (existingAccount.isPresent()) {
            response.put("status", 409);
            return response;
        }

        Account createdAccount = accountRepository.save(newAccount);
        response.put("status", 200);
        response.put("account", createdAccount);
        return response;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Account> account = accountRepository.findByUsernameAndPassword(username, password);
        if (account.isPresent()) {
            response.put("status", 200);
            response.put("account", account.get());
        } else {
            response.put("status", 401);
        }
        return response;
    }

    // Add other account-related methods here
}
