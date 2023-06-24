package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto uploadProfileImage(Long userId, String imageData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        user.setProfileImage(imageData);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    public List<UserDto> searchUsers(String term) {
        List<User> users = userRepository.searchUsers(term);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
