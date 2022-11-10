package com.codestates.mainproject.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private long commentId;
    private String body;
    private long memberId;
    private String memberName;
    private long answerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
