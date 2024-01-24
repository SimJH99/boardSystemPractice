package com.encore.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdTime;
}
