package com.codestates.mainproject.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String acToken;

    private final String rfToken;
}

