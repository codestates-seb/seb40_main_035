package com.codestates.mainproject.domain.skill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SkillResponseDto {
    private long skillId;
    private String name;
}
