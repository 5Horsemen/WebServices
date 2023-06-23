package com.ujobs.WebServices.dto;

import java.time.LocalDateTime;

import com.ujobs.WebServices.model.MessageStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateMessageDto {

    private Long id;
    private String content;
    private LocalDateTime sentDateTime;
    private UserDto sender;
    private UserDto recipient;
    private MessageStatus status;
    private Long chatId;

}
