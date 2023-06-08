package com.ujobs.WebServices.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.MessageDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Chat;
import com.ujobs.WebServices.model.Message;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.MessageRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageDto sendMessage(Chat chat, Long senderId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con el ID: " + senderId));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        message = messageRepository.save(message);

        return mapToDto(message);
    }

    @Override
    public List<MessageDto> getMessagesByChat(Chat chat) {
        List<Message> messages = messageRepository.findByChatIdOrderByTimestampAsc(chat.getId());
        return mapToDtoList(messages);
    }

    private MessageDto mapToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setChatId(message.getChat().getId());
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setSenderName(message.getSender().getName());
        messageDto.setSenderLastName(message.getSender().getLastName());
        messageDto.setContent(message.getContent());
        messageDto.setTimestamp(message.getTimestamp());
        return messageDto;
    }

    private List<MessageDto> mapToDtoList(List<Message> messages) {
        return messages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMessagesByChat(Chat chat) {
        messageRepository.deleteByChat(chat);
    }
}