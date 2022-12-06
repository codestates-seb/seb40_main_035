package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.member.entity.MemberInterest;

import com.codestates.mainproject.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class MemberPostDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String level;

    @NotSpace
    private String github;

    @NotEmpty
    private List<@Valid MemberInterestDto> memberInterests;

    @NotEmpty
    private List<@Valid MemberSkillDto> memberSkills;
}
