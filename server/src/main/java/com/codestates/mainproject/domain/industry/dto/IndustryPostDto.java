package com.codestates.mainproject.domain.industry.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class IndustryPostDto {
    @NotBlank
    private String name;
}
