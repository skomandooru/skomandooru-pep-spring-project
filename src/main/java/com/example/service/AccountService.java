package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.swing.text.html.Option;

@Service
public class AccountService {
    final private AccountRepository accountRepo;

    @Autowired
    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    

    public Account registerAccount(Account newAccount) throws Exception {
        if (newAccount.getUsername().isEmpty() || newAccount.getPassword().length() >= 4) {
            throw new IllegalArgumentException("Invalid account information");
        }

        Account existingAccount = accountRepo.findByUsername(newAccount.getUsername());
        if (existingAccount!= null) {
            throw new Exception("Username already exists");
        }

        return accountRepo.save(newAccount);
    }

    // public Account login(String username, String password) throws AuthenticationException {
    //     Optional<Account> account = accountRepo.findByUsernameAndPassword(username, password);
    //     if (account.isPresent()) {
    //         return account.get();
    //     } else {
    //         throw new AuthenticationException("Invalid credentials");
    //     }
    // }

    public void addAccount(Account newAccount) {
        accountRepo.save(newAccount);
    }

    public Account loginAccount(Account newAccount) {
        return accountRepo.findByUsernameAndPassword(newAccount.getUsername(), newAccount.getPassword());
    }
    
    // Add other account-related methods here
    public boolean checkUsernameAvailability(String username) {
        Account a = accountRepo.findByUsername(username);
        if (a == null) {
            return true;
        }
        return false;
    }

    public boolean validAccount(String username, String password) {
        if (username != "" && password.length() >= 4) {
            return true;
        }
        return false;
    }
}
