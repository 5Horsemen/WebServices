package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.CreatePrivateChatDto;
import com.ujobs.WebServices.dto.PrivateChatDto;

public interface PrivateChatService {

    public abstract PrivateChatDto createPrivateChat(CreatePrivateChatDto createPrivateChatDto);

    public abstract PrivateChatDto getPrivateChatByUsersId(Long user1Id, Long user2Id);

    public abstract List<PrivateChatDto> getPrivateChatByUser1orUser2(Long user1, Long user2);

    public abstract List<PrivateChatDto> getPrivateChatsByUserId(Long userId);
}
