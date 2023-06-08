package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.UserDto;

public interface FollowerService {

    public abstract void follow(UserDto follower, UserDto followed);

    public abstract void unfollow(UserDto follower, UserDto followed);

    public abstract boolean isFollowing(UserDto follower, UserDto followed);

    public abstract List<UserDto> getFollowedUsers(UserDto follower);
}
