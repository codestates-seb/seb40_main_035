package com.codestates.mainproject.domain.heart.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartResponseDto {
    private long heartId;
    private long memberId;
    private long articleId;
}