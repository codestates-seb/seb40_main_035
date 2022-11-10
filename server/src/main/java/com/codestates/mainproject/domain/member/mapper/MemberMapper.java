package com.codestates.mainproject.domain.member.mapper;

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

    MemberResponseDto memberToMemberResponseDto(Member member);

    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
