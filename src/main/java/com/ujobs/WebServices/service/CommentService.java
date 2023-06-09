package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.CommentDto;

public interface CommentService {

    public abstract CommentDto createComment(CommentDto commentDto);

    public abstract CommentDto updateComment(CommentDto commentDto);

    public abstract void deleteComment(Long commentId);

    public abstract CommentDto getCommentById(Long commentId);

    public abstract CommentDto likeComment(Long commentId, Long userId);

    public abstract List<CommentDto> getAllCommentByPostId(Long postId);

}
