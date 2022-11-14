package com.codestates.mainproject.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberInterestDto {
    @NotBlank
    private String interestName;
}
