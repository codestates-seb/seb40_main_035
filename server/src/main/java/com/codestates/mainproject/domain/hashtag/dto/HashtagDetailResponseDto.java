package com.codestates.mainproject.domain.hashtag.dto;

import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HashtagDetailResponseDto {
    private long hashtagId;
    private String name;
    List<ArticleResponseDto> articles;
}
