package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ujobs.WebServices.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  List<User> findByName(String name);

  @Query("SELECT u FROM User u WHERE lower(concat(u.name, ' ', u.lastName)) LIKE lower(concat('%', :term, '%')) OR lower(u.email) LIKE lower(concat('%', :term, '%'))")
  List<User> searchUsers(@Param("term") String term);
}
