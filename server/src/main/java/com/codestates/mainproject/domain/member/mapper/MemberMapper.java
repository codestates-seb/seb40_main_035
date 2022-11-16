package com.codestates.mainproject.domain.member.mapper;

import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.member.dto.MemberDetailResponseDto;
import com.codestates.mainproject.domain.member.dto.LoginDto;
import com.codestates.mainproject.domain.member.dto.MemberPatchDto;
import com.codestates.mainproject.domain.member.dto.MemberPostDto;
import com.codestates.mainproject.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.entity.MemberInterest;
import com.codestates.mainproject.domain.member.entity.MemberSkill;
import com.codestates.mainproject.domain.skill.entity.Skill;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    default Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        Member member = new Member();
        member.setEmail(memberPostDto.getEmail());
        member.setName(memberPostDto.getName());
        member.setPassword(memberPostDto.getPassword());
        member.setPasswordCheck(memberPostDto.getPasswordCheck());

        List<MemberInterest> memberInterests = memberPostDto.getMemberInterests().stream()
                .map(memberInterestDto -> {
                    MemberInterest memberInterest = new MemberInterest();
                    Interest interest = new Interest();
                    interest.setName(memberInterestDto.getInterestName());
                    memberInterest.setInterest(interest);
                    memberInterest.addMember(member);
                    return memberInterest;
                })
                .collect(Collectors.toList());

        member.setMemberInterests(memberInterests);

        List<MemberSkill> memberSkills = memberPostDto.getMemberSkills().stream()
                .map(memberSkillDto -> {
                    MemberSkill memberSkill = new MemberSkill();
                    Skill skill = new Skill();
                    skill.setName(memberSkillDto.getSkillName());
                    memberSkill.setSkill(skill);
                    memberSkill.addMember(member);
                    return memberSkill;
                })
                .collect(Collectors.toList());

        member.setMemberSkills(memberSkills);

        return member;
    }

    default Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        Member member = new Member();
        member.setMemberId(memberPatchDto.getMemberId());
        member.setPassword(memberPatchDto.getPassword());
        member.setPasswordCheck(memberPatchDto.getPasswordCheck());
        member.setName(memberPatchDto.getName());
        member.setDescription(memberPatchDto.getDescription());
        member.setLevel(memberPatchDto.getLevel());
        member.setGithub(memberPatchDto.getGithub());

        if (memberPatchDto.getMemberInterests() != null) {
            List<MemberInterest> memberInterests = memberPatchDto.getMemberInterests().stream()
                    .map(memberInterestDto -> {
                        MemberInterest memberInterest = new MemberInterest();
                        Interest interest = new Interest();
                        interest.setName(memberInterestDto.getInterestName());
                        memberInterest.setInterest(interest);
                        memberInterest.setMember(member);
                        return memberInterest;
                    })
                    .collect(Collectors.toList());

            member.setMemberInterests(memberInterests);
        }

        if (memberPatchDto.getMemberSkills() != null) {
            List<MemberSkill> memberSkills = memberPatchDto.getMemberSkills().stream()
                    .map(memberSkillDto -> {
                        MemberSkill memberSkill = new MemberSkill();
                        Skill skill = new Skill();
                        skill.setName(memberSkillDto.getSkillName());
                        memberSkill.setSkill(skill);
                        memberSkill.setMember(member);
                        return memberSkill;
                    })
                    .collect(Collectors.toList());

            member.setMemberSkills(memberSkills);
        }

        return member;
    }

    MemberDetailResponseDto memberToMemberDetailResponseDto(Member member);

    MemberResponseDto memberToMemberResponseDto(Member member);

    Member loginDtoToMember(LoginDto loginDto);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
