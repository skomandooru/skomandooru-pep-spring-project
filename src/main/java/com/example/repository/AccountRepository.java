package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    static Optional<Account> findByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
    Account findByUsernameAndPassword(String username, String password);
	/**
	 * @return
	 */
	static Optional<Account> findById(final Object postedBy) {
		return null;
	}
}  
