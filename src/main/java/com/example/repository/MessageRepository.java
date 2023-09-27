package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Message SET message_text = :message WHERE message_id = :message_id")
	Integer updateMessage(@Param("message_id") int message_id, @Param("message") String message);

    @Query("FROM Message WHERE message_id = :message_id")
	Message getMessageById(@Param("message_id") int message_id);
}
