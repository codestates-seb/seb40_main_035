package com.codestates.mainproject.domain.skill.dto;

import com.codestates.mainproject.domain.skill.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SkillResponseDto {
    private long skillId;
    private String name;
    private Skill.SkillSort skillSort;

    public String getSkillSort() {
        return skillSort.getSortName();
    }
}
