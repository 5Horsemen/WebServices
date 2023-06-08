package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.MessageDto;
import com.ujobs.WebServices.model.Chat;

public interface MessageService {
    public abstract MessageDto sendMessage(Chat chat, Long senderId, String content);

    public abstract List<MessageDto> getMessagesByChat(Chat chat);

    public abstract void deleteMessagesByChat(Chat chat);
}
