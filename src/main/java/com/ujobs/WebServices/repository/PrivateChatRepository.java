package com.ujobs.WebServices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.PrivateChat;
import com.ujobs.WebServices.model.User;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

    Optional<PrivateChat> findById(Long chatId);

    List<PrivateChat> findByUser1OrUser2(User user1, User user2);

    Optional<PrivateChat> findByUser1AndUser2(User user1, User user2);

    List<PrivateChat> findByUser1(User user1);

    List<PrivateChat> findByUser2(User user2);
}
