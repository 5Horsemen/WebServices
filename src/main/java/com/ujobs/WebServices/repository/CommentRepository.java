package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);
}
