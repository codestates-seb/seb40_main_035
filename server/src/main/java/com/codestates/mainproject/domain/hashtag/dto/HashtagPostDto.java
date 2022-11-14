package com.codestates.mainproject.domain.hashtag.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class HashtagPostDto {
    @NotBlank
    private String name;
}
