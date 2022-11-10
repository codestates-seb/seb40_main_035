package com.codestates.mainproject.domain.answer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {
    private long answerId;
    private String body;
    private long memberId;
    private String memberName;
    private long articleId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
