package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.PostDto;
import com.ujobs.WebServices.model.Post;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.PostRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.PostService;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(userRepository.findById(postDTO.getUserId()).orElse(null));
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(value -> modelMapper.map(value, PostDto.class)).orElse(null);
    }

    @Override
    public PostDto updatePost(PostDto postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(userRepository.findById(postDTO.getUserId()).orElse(null));
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto likePost(Long postId, Long userId) {
        Optional<Post> post = postRepository.findById(postId);
        post.ifPresent(p -> {
            p.getLikes().add(userRepository.findById(userId).orElse(null));
            postRepository.save(p);
        });
        return post.map(this::convertToDto).orElse(null);
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostDto sharePost(Long postId, Long userId) {
        Optional<Post> post = postRepository.findById(postId);
        post.ifPresent(p -> {
            p.getShares().add(userRepository.findById(userId).orElse(null));
            postRepository.save(p);
        });
        return post.map(this::convertToDto).orElse(null);
    }

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setUserId(post.getUser().getId());
        postDto.setImage(post.getImage());

        postDto.setLikes(post.getLikes().stream().map(User::getId).collect(Collectors.toList()));
        postDto.setShares(post.getShares().stream().map(User::getId).collect(Collectors.toList()));

        return postDto;
    }

}
