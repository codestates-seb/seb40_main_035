package com.codestates.mainproject.domain.member.service;

import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.interest.service.InterestService;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.entity.MemberInterest;
import com.codestates.mainproject.domain.member.repository.MemberRepository;

import com.codestates.mainproject.email.service.EmailService;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;

import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.domain.skill.service.SkillService;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final InterestService interestService;
    private final SkillService skillService;

    public Member createMember(Member member) {
        verifyExistingEmail(member.getEmail());
        verifyExistingName(member.getName());
        checkPassword(member.getPassword(), member.getPasswordCheck());

        if (member.getGithub() == null) member.setGithub("");
        else verifyExistingGithub(member.getGithub());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        String encryptedPasswordCheck = passwordEncoder.encode(member.getPasswordCheck());
        member.setPasswordCheck(encryptedPasswordCheck);

        member.getMemberInterests().stream()
                .forEach(memberInterest -> {
                    Interest interest = interestService.findVerifiedInterest(memberInterest.getInterest().getName());
                    memberInterest.setInterest(interest);
                    interest.addMemberInterest(memberInterest);
                });

        member.getMemberSkills().stream()
                .forEach(memberSkill -> {
                    Skill skill = skillService.findVerifiedSkill(memberSkill.getSkill().getName());
                    memberSkill.setSkill(skill);
                    skill.addMemberSkill(memberSkill);
                });

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> {
                    checkPassword(password, member.getPasswordCheck());
                    String encryptedPassword = passwordEncoder.encode(password);
                    findMember.setPassword(encryptedPassword);
                });

        Optional.ofNullable(member.getPasswordCheck())
                .ifPresent(passwordCheck -> {
                    String encryptedPasswordCheck = passwordEncoder.encode(passwordCheck);
                    findMember.setPasswordCheck(encryptedPasswordCheck);
                });

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));

        Optional.ofNullable(member.getDescription())
                .ifPresent(description -> findMember.setDescription(description));

        Optional.ofNullable(member.getLevel())
                .ifPresent(level -> findMember.setLevel(level));

        Optional.ofNullable(member.getGithub())
                .ifPresent(github -> findMember.setGithub(github));

        Optional.ofNullable(member.getMemberInterests())
                .ifPresent(memberInterests -> {
                    if (!memberInterests.isEmpty()) {
                        findMember.getMemberInterests().clear();
                        memberInterests.stream()
                                .forEach(memberInterest -> {
                                    Interest interest = interestService.findVerifiedInterest(memberInterest.getInterest().getName());
                                    memberInterest.setMember(findMember);
                                    memberInterest.setInterest(interest);

                                    findMember.addMemberInterest(memberInterest);
                                    interest.addMemberInterest(memberInterest);
                                });
                    }
                });

        Optional.ofNullable(member.getMemberSkills())
                .ifPresent(memberSkills -> {
                    if (!memberSkills.isEmpty()) {
                        findMember.getMemberSkills().clear();
                        memberSkills.stream()
                                .forEach(memberSkill -> {
                                    Skill skill = skillService.findVerifiedSkill(memberSkill.getSkill().getName());
                                    memberSkill.setMember(findMember);
                                    memberSkill.setSkill(skill);

                                    findMember.addMemberSkill(memberSkill);
                                    skill.addMemberSkill(memberSkill);
                                });
                    }
                });

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("memberId").descending());
        return memberRepository.findAll(pageRequest);
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public void verifyExistingEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }
    }

    public void verifyExistingName(String name) {
        Optional<Member> optionalMember = memberRepository.findByName(name);

        if (optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NAME_ALREADY_EXISTS);
        }
    }

    public void verifyExistingGithub(String github) {
        Optional<Member> optionalMember = memberRepository.findByGithub(github);

        if (optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_GITHUB_ALREADY_EXISTS);
        }
    }

    private void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new BusinessLogicException(ExceptionCode.BAD_PARAMETER_ERROR);
        }
    }

    public Member loginMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_REDIRECTION_FIND_PASSWORD);
        }

        return findMember;
    }

    public Member findVerifiedMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public void passwordChange(Member member, String password) {
        member.setPassword(passwordEncoder.encode(password));
        member.setPasswordCheck(passwordEncoder.encode(password));

        memberRepository.save(member);
    }

    public void deleteGithub(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        member.setGithub("");
        memberRepository.save(member);
    }
}
