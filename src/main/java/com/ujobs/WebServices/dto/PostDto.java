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
public class PostDto {
    private Long id;
    private String content;
    private Long userId;
    private List<Long> likes;
    private List<Long> shares;
    private List<CommentDto> comments;
    private byte[] image;
}
