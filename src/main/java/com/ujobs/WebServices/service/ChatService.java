package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.model.Chat;

public interface ChatService {
    Chat createChat(UserDto user1, UserDto user2);

    List<Chat> getChatsByUser(UserDto user);

    Chat getChatById(Long chatId);

    void deleteChat(Chat chat);
}
