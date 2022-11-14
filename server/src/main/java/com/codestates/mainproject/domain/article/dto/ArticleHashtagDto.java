package com.codestates.mainproject.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class ArticleHashtagDto {
    @NotBlank
    private String hashtagName;
}
