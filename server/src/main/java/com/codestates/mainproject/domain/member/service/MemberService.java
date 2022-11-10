package com.codestates.mainproject.domain.member.service;

import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        verifyExistingEmail(member.getEmail());
        verifyExistingName(member.getName());
        checkPassword(member.getPassword(), member.getPasswordCheck());

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> {
                    checkPassword(member.getPassword(), member.getPasswordCheck());
                    findMember.setPassword(password);
                });

        Optional.ofNullable(member.getPasswordCheck())
                .ifPresent(passwordCheck -> {
                    checkPassword(member.getPassword(), member.getPasswordCheck());
                    findMember.setPasswordCheck(passwordCheck);
                });

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));

        Optional.ofNullable(member.getDescription())
                .ifPresent(description -> findMember.setDescription(description));

        Optional.ofNullable(member.getLevel())
                .ifPresent(level -> findMember.setLevel(level));

        Optional.ofNullable(member.getStack())
                .ifPresent(stack -> {
                    if (!stack.isEmpty()) {
                        findMember.setStack(stack);
                    }
                });

        Optional.ofNullable(member.getField())
                .ifPresent(field -> {
                    if (!field.isEmpty()) {
                        findMember.setField(field);
                    }
                });

        Optional.ofNullable(member.getGithub())
                .ifPresent(github -> findMember.setGithub(github));

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
        return optionalMember.orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
    }

    private void verifyExistingEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            throw new RuntimeException("중복된 이메일입니다.");
        }
    }

    private void verifyExistingName(String name) {
        Optional<Member> optionalMember = memberRepository.findByName(name);

        if (optionalMember.isPresent()) {
            throw new RuntimeException("중복된 이름입니다.");
        }
    }

    private void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new RuntimeException("동일한 비밀번호를 입력하세요.");
        }
    }
}
