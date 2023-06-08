package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
}
