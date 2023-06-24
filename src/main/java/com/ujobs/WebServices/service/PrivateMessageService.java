package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.CreatePrivateMessageDto;
import com.ujobs.WebServices.dto.PrivateMessageDto;

public interface PrivateMessageService {

    public abstract PrivateMessageDto createPrivateMessage(CreatePrivateMessageDto createPrivateMessageDto,
            Long chatId);

    public abstract List<PrivateMessageDto> getPrivateMessagesByChatId(Long chatId);

    public abstract List<PrivateMessageDto> getPrivateMessagesBySenderId(Long senderId);

    public abstract void deletePrivateMessage(Long messageId);
}
