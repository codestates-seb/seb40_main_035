package com.codestates.mainproject.domain.stack.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class StackPostDto {
    @NotBlank
    private String name;
}
