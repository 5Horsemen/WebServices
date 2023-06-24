package com.ujobs.WebServices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.CreatePrivateMessageDto;
import com.ujobs.WebServices.dto.PrivateMessageDto;
import com.ujobs.WebServices.service.PrivateMessageService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PrivateMessageController {

    @Autowired
    private PrivateMessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chats/{chatId}/messages")
    @SendTo("/topic/chats/{chatId}/messages")
    public ResponseEntity<PrivateMessageDto> handleMessage(@DestinationVariable Long chatId,
            @RequestBody CreatePrivateMessageDto createMessageRequest) {
        System.out.println("Received message: " + createMessageRequest.getContent());

        PrivateMessageDto savedMessage = messageService.createPrivateMessage(createMessageRequest, chatId);

        messagingTemplate.convertAndSend("/topic/chats/" + chatId + "/messages", savedMessage);
        System.out.println("Sent message: " + savedMessage.getContent());
        // messagingTemplate.convertAndSend("/topic/chats/*/messages", savedMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<PrivateMessageDto>> getMessagesByChat(@PathVariable Long chatId) {
        List<PrivateMessageDto> messageDtos = messageService.getPrivateMessagesByChatId(chatId);
        return ResponseEntity.ok(messageDtos);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deletePrivateMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
