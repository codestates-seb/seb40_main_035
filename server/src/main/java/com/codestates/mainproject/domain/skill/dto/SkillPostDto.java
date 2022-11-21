package com.codestates.mainproject.domain.skill.dto;

import com.codestates.mainproject.domain.skill.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillPostDto {
    @NotBlank
    private String name;
    @NotNull
    private Skill.SkillSort skillSort;
}
