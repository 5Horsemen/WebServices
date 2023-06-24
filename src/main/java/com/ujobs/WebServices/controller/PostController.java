package com.ujobs.WebServices.controller;

import com.ujobs.WebServices.dto.PostDto;
import com.ujobs.WebServices.exception.ValidationException;
import com.ujobs.WebServices.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // URL: http://localhost:8080/api/v1/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        validatePost(postDto);
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/posts/1/type
    @GetMapping("/{id}/{type}")
    public ResponseEntity<PostDto> getPostByType(@PathVariable Long id, @PathVariable String type) {
        return new ResponseEntity<>(postService.getPostByType(id, type), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/posts/1
    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePost(postDto), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // URL: http://localhost:8080/api/v1/posts/1/like/1
    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<PostDto> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        return new ResponseEntity<>(postService.likePost(postId, userId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/posts/1/share/1
    @PostMapping("/{postId}/share/{userId}")
    public ResponseEntity<PostDto> sharePost(@PathVariable Long postId, @PathVariable Long userId) {
        return new ResponseEntity<>(postService.sharePost(postId, userId), HttpStatus.OK);
    }

    private void validatePost(PostDto postDto) {
        if (postDto.getContent() == null || postDto.getContent().isEmpty()) {
            throw new ValidationException("El contenido de la publicación no puede estar vacío");
        }
        if (postDto.getContent().length() > 5000) {
            throw new ValidationException("El contenido de la publicación no puede tener más de 5000 caracteres");
        }
        if (postDto.getUserId() == null) {
            throw new ValidationException("El usuario de la publicación no puede ser nulo");
        }
    }
}
