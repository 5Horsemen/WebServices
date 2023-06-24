package com.ujobs.WebServices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.CreatePrivateChatDto;
import com.ujobs.WebServices.dto.PrivateChatDto;
import com.ujobs.WebServices.service.PrivateChatService;

@RestController
@RequestMapping("/api/v1/chats")
@CrossOrigin(origins = "http://localhost:4200")
public class PrivateChatController {

    @Autowired
    private PrivateChatService chatService;

    @PostMapping
    public ResponseEntity<PrivateChatDto> createPrivateChat(@RequestBody CreatePrivateChatDto createChatRequest) {
        PrivateChatDto createdChat = chatService.createPrivateChat(createChatRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }

    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<PrivateChatDto> getPrivateChatByUsersId(@PathVariable Long user1Id,
            @PathVariable Long user2Id) {
        PrivateChatDto privateChatDto = chatService.getPrivateChatByUsersId(user1Id, user2Id);
        return ResponseEntity.ok(privateChatDto);
    }

    @GetMapping("/{user1Id}/or/{user2Id}")
    public ResponseEntity<List<PrivateChatDto>> getPrivateChatByUser1orUser2(@PathVariable Long user1Id,
            @PathVariable Long user2Id) {
        List<PrivateChatDto> privateChatDtos = chatService.getPrivateChatByUser1orUser2(user1Id, user2Id);
        return ResponseEntity.ok(privateChatDtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PrivateChatDto>> getChatsByUserId(@PathVariable Long userId) {
        List<PrivateChatDto> chatDtos = chatService.getPrivateChatsByUserId(userId);
        return ResponseEntity.ok(chatDtos);
    }

}