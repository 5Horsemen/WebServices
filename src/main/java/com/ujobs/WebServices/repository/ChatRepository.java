package com.ujobs.WebServices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Chat;
import com.ujobs.WebServices.model.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByUser1AndUser2(User user1, User user2);

    List<Chat> findByUser1(User user1);

    List<Chat> findByUser2(User user2);
}
