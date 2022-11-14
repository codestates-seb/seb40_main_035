package com.codestates.mainproject.domain.article.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ArticleSkillDto {
    @NotBlank
    private String skillName;
}
