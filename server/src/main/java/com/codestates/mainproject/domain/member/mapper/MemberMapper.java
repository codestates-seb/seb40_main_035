package com.codestates.mainproject.domain.member.mapper;

import com.codestates.mainproject.domain.member.dto.MemberDetailResponseDto;
import com.codestates.mainproject.domain.member.dto.LoginDto;
import com.codestates.mainproject.domain.member.dto.MemberPatchDto;
import com.codestates.mainproject.domain.member.dto.MemberPostDto;
import com.codestates.mainproject.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.domain.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);

    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);

    MemberDetailResponseDto memberToMemberDetailResponseDto(Member member);

    MemberResponseDto memberToMemberResponseDto(Member member);

    Member loginDtoToMember(LoginDto loginDto);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
