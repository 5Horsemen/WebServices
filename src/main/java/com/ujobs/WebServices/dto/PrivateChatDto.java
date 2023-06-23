package com.ujobs.WebServices.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateChatDto {
    private Long id;
    private UserDto user1;
    private UserDto user2;
    private List<PrivateMessageDto> messages;
}
