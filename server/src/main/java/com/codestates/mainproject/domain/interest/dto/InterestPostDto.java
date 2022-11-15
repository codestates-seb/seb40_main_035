package com.codestates.mainproject.domain.interest.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class InterestPostDto {
    @NotBlank
    private String name;
}
