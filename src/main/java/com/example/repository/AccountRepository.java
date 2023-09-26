package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer>{
 
	@Query("FROM Account WHERE username = :username")
	Account findByUsername(@Param("username") String username);

	@Query("FROM Account WHERE account_id = :account_id")
	Account findByAccount(@Param("account_id") int account_id);

	@Query("FROM Account WHERE username = :username AND password = :password")
	Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}  
