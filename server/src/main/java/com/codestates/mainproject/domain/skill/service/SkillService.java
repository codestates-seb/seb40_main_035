package com.codestates.mainproject.domain.skill.service;

import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.domain.skill.repository.SkillRepository;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository repository;

    public Skill createSkill(Skill skill) {
        verifyExistingName(skill.getName());
        return repository.save(skill);
    }

    public Skill updateSkill(Skill skill) {
        Skill findSkill = findVerifiedSkill(skill.getSkillId());

        Optional.ofNullable(skill.getName())
                .ifPresent(name -> findSkill.setName(name));
        Optional.ofNullable(skill.getSkillSort())
                .ifPresent(skillSort -> findSkill.setSkillSort(skillSort));

        return repository.save(findSkill);
    }

    public Skill findSkill(long skillId) {
        return findVerifiedSkill(skillId);
    }

    public List<Skill> findSkills() {
        return repository.findAll();
    }

    public void deleteSkill(long skillId) {
        Skill skill = findVerifiedSkill(skillId);
        repository.delete(skill);
    }

    public Skill findVerifiedSkill(long skillId) {
        Optional<Skill> optionalSkill = repository.findById(skillId);
        return optionalSkill.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SKILL_NOT_FOUND));
    }

    public Skill findVerifiedSkill(String name) {
        Optional<Skill> optionalSkill = repository.findByName(name);
        return optionalSkill.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SKILL_NOT_FOUND));
    }

    private void verifyExistingName(String name) {
        Optional<Skill> optionalSkill = repository.findByName(name);

        if (optionalSkill.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.SKILL_ALREADY_EXISTS);
        }
    }
}
