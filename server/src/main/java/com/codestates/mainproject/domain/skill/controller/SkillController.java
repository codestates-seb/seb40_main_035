package com.codestates.mainproject.domain.skill.controller;

import com.codestates.mainproject.domain.skill.dto.SkillPatchDto;
import com.codestates.mainproject.domain.skill.dto.SkillPostDto;
import com.codestates.mainproject.domain.skill.dto.SkillResponseDto;
import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.domain.skill.mapper.SkillMapper;
import com.codestates.mainproject.domain.skill.service.SkillService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/skills")
@Validated
@RequiredArgsConstructor
public class SkillController {
    private final SkillService service;
    private final SkillMapper mapper;

    @PostMapping
    public ResponseEntity postSkill(@Valid @RequestBody SkillPostDto postDto) {

        Skill skill = mapper.skillPostDtoToSkill(postDto);
        Skill createdSkill = service.createSkill(skill);
        SkillResponseDto responseDto = mapper.skillToSkillResponseDto(createdSkill);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{skill-id}")
    public ResponseEntity patchSkill(@PathVariable("skill-id") @Positive long skillId,
                                     @Valid @RequestBody SkillPatchDto patchDto) {
        patchDto.setSkillId(skillId);

        Skill skill = mapper.skillPatchDtoToSkill(patchDto);
        Skill updatedSkill = service.updateSkill(skill);
        SkillResponseDto responseDto = mapper.skillToSkillResponseDto(updatedSkill);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{skill-id}")
    public ResponseEntity getSkill(@PathVariable("skill-id") @Positive long skillId) {

        Skill foundSkill = service.findSkill(skillId);
        SkillResponseDto responseDto = mapper.skillToSkillResponseDto(foundSkill);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getSkills() {

        List<Skill> skills = service.findSkills();
        List<SkillResponseDto> responseDtos = mapper.skillsToSkillResponseDtos(skills);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{skill-id}")
    public ResponseEntity deleteSkill(@PathVariable("skill-id") @Positive long skillId) {

        service.deleteSkill(skillId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

