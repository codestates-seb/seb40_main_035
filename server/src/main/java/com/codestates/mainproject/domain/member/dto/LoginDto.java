package com.codestates.mainproject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {

    private final String email;

    private final String password;
}
