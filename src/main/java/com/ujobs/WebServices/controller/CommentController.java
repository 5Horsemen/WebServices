package com.ujobs.WebServices.controller;

import com.ujobs.WebServices.dto.CommentDto;
import com.ujobs.WebServices.exception.ValidationException;
import com.ujobs.WebServices.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        validateComment(commentDto);
        return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{commentId}/like/{userId}")
    public ResponseEntity<CommentDto> likeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        return new ResponseEntity<>(commentService.likeComment(commentId, userId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.getAllCommentByPostId(postId), HttpStatus.OK);
    }

    private void validateComment(CommentDto commentDto) {
        if (commentDto.getContent() == null || commentDto.getContent().isEmpty()) {
            throw new ValidationException("El contenido del comentario no puede estar vacío");
        }
        if (commentDto.getContent().length() > 5000) {
            throw new ValidationException("El contenido del comentario no puede tener más de 5000 caracteres");
        }
        if (commentDto.getUser() == null) {
            throw new ValidationException("El usuario del comentario no puede ser nulo");
        }
        if (commentDto.getPostId() == null) {
            throw new ValidationException("El post del comentario no puede ser nulo");
        }
    }
}
