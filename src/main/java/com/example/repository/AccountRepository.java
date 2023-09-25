package com.example.repository;

import java.util.Optional;

import com.example.entity.Account;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findById(Object postedBy);
}
