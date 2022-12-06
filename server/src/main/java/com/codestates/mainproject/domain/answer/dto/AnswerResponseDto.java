package com.codestates.mainproject.domain.answer.dto;

import com.codestates.mainproject.domain.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResponseDto {
    private long answerId;
    private String body;
    private long memberId;
    private String memberName;
    private long articleId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;
}
