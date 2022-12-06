package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class EmailDto {
    @Email
    @NotBlank
    private String email;

    @NotSpace
    private String code;
}
