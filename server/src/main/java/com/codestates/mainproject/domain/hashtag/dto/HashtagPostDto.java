package com.codestates.mainproject.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagPostDto {
    @NotBlank
    private String name;
}
