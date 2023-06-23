package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.PrivateMessage;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {

    List<PrivateMessage> findByChatId(Long privateChatId);

    List<PrivateMessage> findBySenderId(Long senderId);
}
