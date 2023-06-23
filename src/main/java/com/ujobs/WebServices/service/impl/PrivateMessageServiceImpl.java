package com.ujobs.WebServices.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.CreatePrivateMessageDto;
import com.ujobs.WebServices.dto.PrivateMessageDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.PrivateChat;
import com.ujobs.WebServices.model.PrivateMessage;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.PrivateChatRepository;
import com.ujobs.WebServices.repository.PrivateMessageRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.service.PrivateMessageService;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivateMessageRepository privateMessageRepository;
    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PrivateMessageDto createPrivateMessage(CreatePrivateMessageDto createPrivateMessageDto, Long chatId) {

        Long senderId = createPrivateMessageDto.getSenderId();
        Long recipientId = createPrivateMessageDto.getRecipientId();
        String content = createPrivateMessageDto.getContent();

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Remitente no encontrado"));

        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new ResourceNotFoundException("Destinatario no encontrado"));

        PrivateMessage privateMessage = new PrivateMessage();

        PrivateChat chat = privateChatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat no encontrado"));

        privateMessage.setChat(chat);
        privateMessage.setSender(sender);
        privateMessage.setRecipient(recipient);
        privateMessage.setContent(content);
        privateMessage.setSentDateTime(LocalDateTime.now());

        privateMessageRepository.save(privateMessage);

        PrivateMessageDto privateMessageDto = modelMapper.map(privateMessage, PrivateMessageDto.class);
        return privateMessageDto;
    }

    @Override
    public List<PrivateMessageDto> getPrivateMessagesByChatId(Long chatId) {
        List<PrivateMessage> messages = privateMessageRepository.findByChatId(chatId);

        List<PrivateMessageDto> messagesDto = messages.stream()
                .map(message -> modelMapper.map(message, PrivateMessageDto.class))
                .collect(Collectors.toList());

        return messagesDto;
    }

    @Override
    public List<PrivateMessageDto> getPrivateMessagesBySenderId(Long senderId) {
        List<PrivateMessage> messages = privateMessageRepository.findBySenderId(senderId);

        List<PrivateMessageDto> messagesDto = messages.stream()
                .map(message -> modelMapper.map(message, PrivateMessageDto.class))
                .collect(Collectors.toList());

        return messagesDto;
    }

    @Override
    public void deletePrivateMessage(Long messageId) {
        PrivateMessage message = privateMessageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado"));
        privateMessageRepository.delete(message);

    }

}
