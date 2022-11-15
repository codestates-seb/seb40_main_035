package com.codestates.mainproject.domain.member.controller;

import com.codestates.mainproject.domain.member.dto.LoginDto;
import com.codestates.mainproject.domain.member.dto.MemberDetailResponseDto;
import com.codestates.mainproject.domain.member.dto.MemberPatchDto;
import com.codestates.mainproject.domain.member.dto.MemberPostDto;
import com.codestates.mainproject.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.mapper.MemberMapper;
import com.codestates.mainproject.domain.member.service.MemberService;
import com.codestates.mainproject.dto.MultiResponseDto;
import com.codestates.mainproject.dto.PageInfo;
import com.codestates.mainproject.dto.SingleResponseDto;
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

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.security.Principal;
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

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto postDto) {

        Member member = mapper.memberPostDtoToMember(postDto);
        Member createdMember = memberService.createMember(member);
        MemberDetailResponseDto responseDto = mapper.memberToMemberDetailResponseDto(createdMember);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
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
        PageInfo pageInfo = new PageInfo(memberPage.getNumber()+1, memberPage.getSize(), memberPage.getTotalElements(), memberPage.getTotalPages());

        return new ResponseEntity<>(new MultiResponseDto(responseDtos, pageInfo), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {

        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reissue")
    public ResponseEntity reissue(@AuthenticationPrincipal MemberDetails memberDetails) throws JwtException {
        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(memberDetails.getMember());
        TokenResponse tokenResponseDto = jwtTokenizer.reissueAcTken(responseDto);

        Map<String, Object> claims = jwtTokenizer.getClaims(tokenResponseDto.getAcToken()).getBody();
        long memberId = Long.parseLong(claims.get("memberId").toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenResponseDto.getAcToken());

        return new ResponseEntity<>(new SingleResponseDto<>(memberId), headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) throws JsonProcessingException {

        Member member = mapper.loginDtoToMember(loginDto);
        Member authorizedMember = memberService.loginMember(member);
        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(authorizedMember);

        TokenResponse tokenResponse = jwtTokenizer.createTokensByLogin(responseDto);

        Map<String, Object> claims = jwtTokenizer.getClaims(tokenResponse.getAcToken()).getBody();
        long memberId = Long.parseLong(claims.get("memberId").toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenResponse.getAcToken());
        headers.add("Refresh", tokenResponse.getRfToken());

        return new ResponseEntity<>(new SingleResponseDto<>(memberId), headers, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestHeader("Authorization") String bearerAtk) throws JwtException {
        MemberResponseDto memberResponseDto = mapper.memberToMemberResponseDto(memberDetails.getMember());

        jwtTokenizer.setBlackListAcToken(bearerAtk);
        jwtTokenizer.deleteRfToken(memberResponseDto);

        return new ResponseEntity<>(new SingleResponseDto<>("로그아웃이 완료되었습니다."), HttpStatus.NO_CONTENT);
    }
}

