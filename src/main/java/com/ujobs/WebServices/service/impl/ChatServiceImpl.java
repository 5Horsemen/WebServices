package com.ujobs.WebServices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Chat;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.ChatRepository;
import com.ujobs.WebServices.repository.MessageRepository;
import com.ujobs.WebServices.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public ChatServiceImpl(ChatRepository chatRepository, MessageRepository messageRepository,
            ModelMapper modelMapper) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Chat createChat(UserDto user1Dto, UserDto user2Dto) {
        User user1 = modelMapper.map(user1Dto, User.class);
        User user2 = modelMapper.map(user2Dto, User.class);

        Optional<Chat> existingChat = chatRepository.findByUser1AndUser2(user1, user2);
        if (existingChat.isPresent()) {
            return existingChat.get();
        }

        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getChatsByUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        List<Chat> chats1 = chatRepository.findByUser1(user);
        List<Chat> chats2 = chatRepository.findByUser2(user);
        List<Chat> chats = new ArrayList<>(chats1);
        chats.addAll(chats2);
        return chats;
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found with id: " + chatId));
    }

    @Override
    public void deleteChat(Chat chat) {
        messageRepository.deleteByChat(chat);
        chatRepository.delete(chat);
    }
}
