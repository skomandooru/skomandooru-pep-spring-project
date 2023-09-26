package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

import javax.naming.AuthenticationException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account newAccount) throws Exception {
        if (newAccount.getUsername().isEmpty() || newAccount.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid account information");
        }

        Optional<Account> existingAccount = AccountRepository.findByUsername(newAccount.getUsername());
        if (existingAccount.isPresent()) {
            throw new Exception("Username already exists");
        }

        return accountRepository.save(newAccount);
    }

    public Account login(String username, String password) throws AuthenticationException {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new AuthenticationException("Invalid credentials");
        }
    }
    
    // Add other account-related methods here
}
