package com.codestates.mainproject.domain.skill.mapper;

import com.codestates.mainproject.domain.skill.dto.SkillPatchDto;
import com.codestates.mainproject.domain.skill.dto.SkillPostDto;
import com.codestates.mainproject.domain.skill.dto.SkillResponseDto;
import com.codestates.mainproject.domain.skill.entity.Skill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill skillPostDtoToSkill(SkillPostDto postDto);

    Skill skillPatchDtoToSkill(SkillPatchDto patchDto);

    SkillResponseDto skillToSkillResponseDto(Skill skill);

    List<SkillResponseDto> skillsToSkillResponseDtos(List<Skill> skills);
}
