package com.codestates.mainproject.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class HashtagPostDto {
    @NotBlank
    private String name;
}
