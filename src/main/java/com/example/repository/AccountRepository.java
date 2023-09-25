package com.example.repository;

import java.util.Optional;

import com.example.entity.Account;

public interface AccountRepository {

    static Account save(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    static Optional<Account> findByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findById(Object postedBy);
}
