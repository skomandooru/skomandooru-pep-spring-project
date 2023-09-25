package com.example.service;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    public AccountRepository accountRepo;
    private String username;

    @Autowired
    public AccountService (AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public boolean doesUsernameExist(String username) {
        Optional<Account> existingUser = AccountRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        Optional<Account> existingUser = AccountRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            // You should implement password validation logic here, e.g., hashing and comparing.
            String storedPassword = existingUser.get().getPassword();
            // Implement password hashing and validation logic, don't compare plaintext passwords.
            // For security reasons, consider using a library like Spring Security for authentication.
            boolean isValidPassword = validatePassword(password, storedPassword);
            return isValidPassword;
        }

        return false; // User not found
    }

    private boolean validatePassword(String password, String storedPassword) {
        return false;
    }

    /**
     * @param username
     * @param password
     */
    public AccountService(String username, String password) {
        // Check if the username already exists
        if (doesUsernameExist(username)) {
            return; // Username already exists
        }

        // You should implement password hashing here before storing it in the database.
        // For example, you can use Spring Security's BCryptPasswordEncoder.
        // String passwordHash = encodePassword(password);

        // Create a new user entity and save it to the database
        setUsername(username);

        // Set the password hash here
        AccountService.setPassword(password);
        //AccountRepository.save(newUser);

        return; // Account created successfully
    }

    private static void setPassword(String password) {
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> login(String string, String string2) {
        return null;
    }
}
