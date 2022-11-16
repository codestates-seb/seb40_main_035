package com.codestates.mainproject.domain.skill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillPostDto {
    @NotBlank
    private String name;
}
