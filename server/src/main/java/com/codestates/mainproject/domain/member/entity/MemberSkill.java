package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.domain.skill.entity.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberSkillId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "SKILL_ID")
    private Skill skill;

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getMemberSkills().contains(this)) {
            this.member.getMemberSkills().add(this);
        }
    }
}
