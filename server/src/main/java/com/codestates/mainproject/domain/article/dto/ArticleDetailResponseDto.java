package com.codestates.mainproject.domain.article.dto;

import com.codestates.mainproject.domain.answer.dto.AnswerResponseDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDetailResponseDto {
    private long articleId;
    private String title;
    private String body;
    private long views;
    private Boolean isCompleted;
    private String startDay;
    private String endDay;
    private int backend;
    private int frontend;
    private long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int heartCount;
    private int answerCount;
    private List<HashtagResponseDto> hashtags;
    private List<AnswerResponseDto> answers;
}
