package com.codestates.mainproject.domain.skill.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SkillPostDto {
    @NotBlank
    private String name;
}
