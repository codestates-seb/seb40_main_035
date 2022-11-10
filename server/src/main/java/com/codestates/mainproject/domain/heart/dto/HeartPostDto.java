package com.codestates.mainproject.domain.heart.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class HeartPostDto {
    @Positive
    private long memberId;

    @Positive
    private long articleId;

}
