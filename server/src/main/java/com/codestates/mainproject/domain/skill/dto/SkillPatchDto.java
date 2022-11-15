package com.codestates.mainproject.domain.skill.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillPatchDto {
    private long skillId;

    @NotSpace
    private String name;
}
