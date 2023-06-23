package com.ujobs.WebServices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrivateMessageDto {
    private String content;
    private Long senderId;
    private Long recipientId;
}
