package com.ujobs.WebServices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.service.FollowerService;
import com.ujobs.WebServices.service.UserService;

@RestController
@RequestMapping("/api/v1/followers")
@CrossOrigin(origins = "http://localhost:4200")
public class FollowerController {
    private final FollowerService followerService;
    private final UserService userService;

    public FollowerController(FollowerService followerService, UserService userService) {
        this.followerService = followerService;
        this.userService = userService;

    }

    @PostMapping("/follow/{followerId}/{followedId}")
    public ResponseEntity<?> follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        UserDto followerDto = userService.getUserById(followerId);
        UserDto followedDto = userService.getUserById(followedId);

        followerService.follow(followerDto, followedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unfollow/{followerId}/{followedId}")
    public ResponseEntity<?> unfollow(@PathVariable Long followerId, @PathVariable Long followedId) {
        UserDto followerDto = userService.getUserById(followerId);
        UserDto followedDto = userService.getUserById(followedId);

        followerService.unfollow(followerDto, followedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/isFollowing/{followerId}/{followedId}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Long followerId, @PathVariable Long followedId) {
        UserDto followerDto = userService.getUserById(followerId);
        UserDto followedDto = userService.getUserById(followedId);

        boolean isFollowing = followerService.isFollowing(followerDto, followedDto);
        return new ResponseEntity<>(isFollowing, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/followed")
    public ResponseEntity<List<UserDto>> getFollowedUsers(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        List<UserDto> followedUsers = followerService.getFollowedUsers(userDto);
        return new ResponseEntity<>(followedUsers, HttpStatus.OK);
    }
}
