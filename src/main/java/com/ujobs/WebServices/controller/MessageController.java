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

import com.ujobs.WebServices.dto.MessageDto;
import com.ujobs.WebServices.model.Chat;
import com.ujobs.WebServices.requests.CreateMessageRequest;
import com.ujobs.WebServices.service.ChatService;
import com.ujobs.WebServices.service.MessageService;

@RestController
@RequestMapping("/api/v1/chats/{chatId}/messages")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    private ChatService chatService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessagesByChat(@PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        List<MessageDto> messages = messageService.getMessagesByChat(chat);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@PathVariable Long chatId,
            @RequestBody CreateMessageRequest request) {
        Chat chat = chatService.getChatById(chatId);
        MessageDto message = messageService.sendMessage(chat, request.getSenderId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}