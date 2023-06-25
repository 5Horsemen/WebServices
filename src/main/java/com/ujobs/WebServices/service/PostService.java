package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.PostDto;

public interface PostService {

    public abstract PostDto createPost(PostDto post);

    public abstract PostDto getPost(Long id);

    public abstract List<PostDto> getAllPosts();

    public abstract PostDto updatePost(PostDto post);

    public abstract void deletePost(Long id);

    public abstract PostDto likePost(Long postId, Long userId);

    public abstract List<PostDto> getAllPostsByUserId(Long userId);

    public abstract PostDto sharePost(Long postId, Long userId);

}
