package com.codestates.mainproject.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String password;
    private String name;
    private String description;
    private String level;
    private String github;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
