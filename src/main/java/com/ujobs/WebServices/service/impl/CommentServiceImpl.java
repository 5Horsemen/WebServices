package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.CommentDto;
import com.ujobs.WebServices.model.Comment;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.CommentRepository;
import com.ujobs.WebServices.repository.PostRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setUser(userRepository.findById(commentDto.getUser().getId()).orElse(null));
        comment.setPost(postRepository.findById(commentDto.getPostId()).orElse(null));
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElse(null);
        if (comment != null) {
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
        }
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.map(value -> modelMapper.map(value, CommentDto.class)).orElse(null);
    }

    @Override
    public CommentDto likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (comment != null && user != null) {
            comment.getLikes().add(user);
            commentRepository.save(comment);
        }
        return comment != null ? modelMapper.map(comment, CommentDto.class) : null;
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}
