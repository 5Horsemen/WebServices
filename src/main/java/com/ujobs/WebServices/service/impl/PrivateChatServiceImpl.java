package com.ujobs.WebServices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.CreatePrivateChatDto;
import com.ujobs.WebServices.dto.PrivateChatDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.PrivateChat;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.PrivateChatRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.PrivateChatService;

@Service
public class PrivateChatServiceImpl implements PrivateChatService {

    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PrivateChatDto createPrivateChat(CreatePrivateChatDto createPrivateChatDto) {
        User user1 = userRepository.findById(createPrivateChatDto.getUser1Id())
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));
        User user2 = userRepository.findById(createPrivateChatDto.getUser2Id())
                .orElseThrow(() -> new ResourceNotFoundException("User2 not found"));

        // Verificar si ya existe un chat privado entre los dos usuarios en cualquier
        // dirección
        Optional<PrivateChat> existingChat = privateChatRepository.findByUser1AndUser2(user1, user2);
        if (existingChat.isPresent()) {
            return modelMapper.map(existingChat.get(), PrivateChatDto.class);
        }

        // Verificar si ya existe un chat privado entre los dos usuarios en la dirección
        // inversa
        existingChat = privateChatRepository.findByUser1AndUser2(user2, user1);
        if (existingChat.isPresent()) {
            return modelMapper.map(existingChat.get(), PrivateChatDto.class);
        }

        PrivateChat privateChat = new PrivateChat();
        privateChat.setUser1(user1);
        privateChat.setUser2(user2);
        privateChatRepository.save(privateChat);

        return modelMapper.map(privateChat, PrivateChatDto.class);
    }

    @Override
    public PrivateChatDto getPrivateChatByUsersId(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("User2 not found"));

        Optional<PrivateChat> privateChat = privateChatRepository.findByUser1AndUser2(user1, user2);
        if (privateChat.isPresent()) {
            return modelMapper.map(privateChat.get(), PrivateChatDto.class);
        }

        privateChat = privateChatRepository.findByUser1AndUser2(user2, user1);
        if (privateChat.isPresent()) {
            return modelMapper.map(privateChat.get(), PrivateChatDto.class);
        }

        throw new ResourceNotFoundException("Private chat not found");
    }

    @Override
    public List<PrivateChatDto> getPrivateChatByUser1orUser2(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("User2 not found"));

        List<PrivateChat> privateChats = privateChatRepository.findByUser1OrUser2(user1, user2);
        return privateChats.stream()
                .map(privateChat -> modelMapper.map(privateChat, PrivateChatDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PrivateChatDto> getPrivateChatsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<PrivateChat> privateChatsUser1 = privateChatRepository.findByUser1(user);
        List<PrivateChat> privateChatsUser2 = privateChatRepository.findByUser2(user);

        List<PrivateChatDto> privateChatDtos = new ArrayList<>();

        privateChatDtos.addAll(privateChatsUser1.stream()
                .map(privateChat -> modelMapper.map(privateChat, PrivateChatDto.class))
                .collect(Collectors.toList()));

        privateChatDtos.addAll(privateChatsUser2.stream()
                .map(privateChat -> modelMapper.map(privateChat, PrivateChatDto.class))
                .collect(Collectors.toList()));

        return privateChatDtos;
    }

}
