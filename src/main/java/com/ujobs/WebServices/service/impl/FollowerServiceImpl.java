package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.model.Follower;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.FollowerRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FollowerServiceImpl(FollowerRepository followerRepository, UserRepository userRepository,
            ModelMapper modelMapper) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void follow(UserDto followerDto, UserDto followedDto) {
        User follower = userRepository.findById(followerDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario seguidor no encontrado"));
        User followed = userRepository.findById(followedDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario seguido no encontrado"));

        if (!isFollowing(followerDto, followedDto)) {
            Follower followRelation = new Follower();
            followRelation.setFollower(follower);
            followRelation.setFollowed(followed);
            followerRepository.save(followRelation);
        }
    }

    @Override
    @Transactional
    public void unfollow(UserDto followerDto, UserDto followedDto) {
        if (isFollowing(followerDto, followedDto)) {
            User follower = userRepository.findById(followerDto.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario seguidor no encontrado"));
            User followed = userRepository.findById(followedDto.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario seguido no encontrado"));
            followerRepository.deleteByFollowerAndFollowed(follower, followed);
        }
    }

    @Override
    public boolean isFollowing(UserDto followerDto, UserDto followedDto) {
        User follower = userRepository.findById(followerDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario seguidor no encontrado"));
        User followed = userRepository.findById(followedDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario seguido no encontrado"));

        return followerRepository.existsByFollowerAndFollowed(follower, followed);
    }

    public List<UserDto> getFollowedUsers(UserDto followerDto) {
        User follower = userRepository.findById(followerDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario seguidor no encontrado"));

        return followerRepository.findByFollower(follower)
                .stream()
                .map(Follower::getFollowed)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
