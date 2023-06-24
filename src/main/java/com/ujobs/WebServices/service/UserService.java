package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.UserDto;

public interface UserService {
    public abstract UserDto getUserById(Long id);

    public abstract UserDto getUserByEmail(String email);

    public abstract UserDto uploadProfileImage(Long userId, String imageData);

    public abstract List<UserDto> searchUsers(String term);
}
