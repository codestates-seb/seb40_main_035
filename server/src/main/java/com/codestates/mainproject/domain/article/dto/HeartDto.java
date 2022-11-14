package com.codestates.mainproject.domain.article.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class HeartDto {
    @Positive
    private long memberId;
}
