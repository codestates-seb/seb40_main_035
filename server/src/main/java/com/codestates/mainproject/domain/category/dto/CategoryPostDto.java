package com.codestates.mainproject.domain.category.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Getter
public class CategoryPostDto {
    @NotBlank
    private String name;
}
