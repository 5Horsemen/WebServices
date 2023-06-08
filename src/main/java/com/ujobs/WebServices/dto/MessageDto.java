package com.ujobs.WebServices.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String senderName;
    private String senderLastName;
    private String content;
    private LocalDateTime timestamp;
}
