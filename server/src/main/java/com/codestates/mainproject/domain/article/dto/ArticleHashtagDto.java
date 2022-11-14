package com.codestates.mainproject.domain.article.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class ArticleHashtagDto {
    @NotBlank
    private String hashtagName;
}
