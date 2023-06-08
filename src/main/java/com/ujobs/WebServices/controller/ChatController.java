package com.ujobs.WebServices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.ChatDto;
import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Chat;
import com.ujobs.WebServices.requests.CreateChatRequest;
import com.ujobs.WebServices.service.ChatService;
import com.ujobs.WebServices.service.UserService;

@RestController
@RequestMapping("/api/v1/chats")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ChatController(ChatService chatService, UserService userService, ModelMapper modelMapper) {
        this.chatService = chatService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDto>> getChatsByUser(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        List<Chat> chats = chatService.getChatsByUser(user);
        List<ChatDto> chatDtos = chats.stream()
                .map(chat -> modelMapper.map(chat, ChatDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(chatDtos);
    }

    @PostMapping
    public ResponseEntity<ChatDto> createChat(@RequestBody CreateChatRequest request) {
        UserDto user1Dto = userService.getUserById(request.getUser1Id());
        UserDto user2Dto = userService.getUserById(request.getUser2Id());

        Chat chat = chatService.createChat(user1Dto, user2Dto);
        ChatDto chatDto = modelMapper.map(chat, ChatDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(chatDto);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        try {
            Chat chat = chatService.getChatById(chatId);
            chatService.deleteChat(chat);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // URl: http://localhost:8080/api/v1/chats/{id}

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDto> getChatById(@PathVariable Long chatId) {
        try {
            Chat chat = chatService.getChatById(chatId);
            ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(chatDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
