package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ujobs.WebServices.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  List<User> findByName(String name);

}
