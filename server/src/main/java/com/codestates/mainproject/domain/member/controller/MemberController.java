package com.codestates.mainproject.domain.member.controller;

import com.codestates.mainproject.domain.member.dto.*;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.mapper.MemberMapper;
import com.codestates.mainproject.domain.member.service.MemberService;
import com.codestates.mainproject.dto.MultiResponseDto;
import com.codestates.mainproject.dto.PageInfo;
import com.codestates.mainproject.dto.SingleResponseDto;
import com.codestates.mainproject.email.service.EmailService;
import com.codestates.mainproject.security.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.security.auth.jwt.MemberDetails;
import com.codestates.mainproject.security.response.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
@Validated
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final JwtTokenizer jwtTokenizer;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto postDto) {

        Member member = mapper.memberPostDtoToMember(postDto);
        Member createdMember = memberService.createMember(member);
        MemberDetailResponseDto responseDto = mapper.memberToMemberDetailResponseDto(createdMember);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PostMapping("/signup/email-send")
    public ResponseEntity sendEmail(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {

        memberService.verifyExistingEmail(emailDto.getEmail());
        emailService.sendAuthMail(emailDto.getEmail());

        return new ResponseEntity<>(new SingleResponseDto<>("입력한 이메일로 인증코드가 발송되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/signup/email-auth")
    public ResponseEntity authEmail(@RequestBody EmailDto authDto) {

        emailService.verifyAuthCode(authDto.getEmail(), authDto.getCode());

        return new ResponseEntity<>(new SingleResponseDto<>("이메일이 인증 되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/signup/verify-name")
    public ResponseEntity verifyName(@RequestBody VerifyNameDto verifyNameDto) {

        memberService.verifyExistingName(verifyNameDto.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto patchDto) {

        patchDto.setMemberId(memberId);

        Member member = mapper.memberPatchDtoToMember(patchDto);
        Member updatedMember = memberService.updateMember(member);
        MemberDetailResponseDto responseDto = mapper.memberToMemberDetailResponseDto(updatedMember);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

//    @GetMapping("/{member-id}/delete-github")
//    public ResponseEntity deleteGithub(@PathVariable("member-id") @Positive long memberId) {
//        memberService.deleteGithub(memberId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {

        Member foundMember = memberService.findMember(memberId);
        MemberDetailResponseDto responseDto = mapper.memberToMemberDetailResponseDto(foundMember);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam("page") @Positive int page,
                                     @RequestParam("size") @Positive int size) {

        Page<Member> memberPage = memberService.findMembers(page, size);
        List<Member> members = memberPage.getContent();
        List<MemberResponseDto> responseDtos = mapper.membersToMemberResponseDtos(members);
        PageInfo pageInfo = new PageInfo(memberPage.getNumber() + 1, memberPage.getSize(), memberPage.getTotalElements(), memberPage.getTotalPages());

        return new ResponseEntity<>(new MultiResponseDto(responseDtos, pageInfo), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {

        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reissue")
    public ResponseEntity reissue(@AuthenticationPrincipal MemberDetails memberDetails) throws JwtException {
        Member member = memberService.findVerifiedMember(memberDetails.getMember().getEmail());
        TokenResponse tokenResponseDto = jwtTokenizer.reissueAcToken(member);

        Map<String, Object> claims = jwtTokenizer.getClaims(tokenResponseDto.getAcToken()).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenResponseDto.getAcToken());
        headers.add("memberId", claims.get("memberId").toString());

        return new ResponseEntity<>(new SingleResponseDto<>("엑세스 토큰이 재발급되었습니다."), headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) throws JsonProcessingException {

        Member member = mapper.loginDtoToMember(loginDto);
        Member authorizedMember = memberService.loginMember(member);

        TokenResponse tokenResponse = jwtTokenizer.createTokensByLogin(authorizedMember);

        Map<String, Object> claims = jwtTokenizer.getClaims(tokenResponse.getAcToken()).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenResponse.getAcToken());
        headers.add("Refresh", tokenResponse.getRfToken());
        headers.add("memberId", claims.get("memberId").toString());

        return new ResponseEntity<>(new SingleResponseDto<>("로그인에 성공하였습니다."), headers, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestHeader("Authorization") String bearerAtk) throws JwtException {
        Member member = memberService.findVerifiedMember(memberDetails.getMember().getEmail());

        jwtTokenizer.setBlackListAcToken(bearerAtk);
        jwtTokenizer.deleteRfToken(member);

        return new ResponseEntity<>(new SingleResponseDto<>("로그아웃이 완료되었습니다."), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/find-password")
    public ResponseEntity sendPasswordEmail(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {

        Member member = memberService.findVerifiedMember(emailDto.getEmail());
        String password = emailService.sendPasswordMail(emailDto.getEmail());
        memberService.passwordChange(member, password);

        return new ResponseEntity<>(new SingleResponseDto<>("입력한 이메일로 임시 비밀번호가 발송되었습니다."), HttpStatus.OK);
    }
}

