package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
@Getter
@Setter
public class MemberPatchDto {
    private long memberId;

    @NotSpace
    private String password;

    @NotSpace
    private String passwordCheck;

    @NotSpace
    private String name;

    @NotSpace
    private String description;

    @NotSpace
    private String level;

    @NotSpace
    private String github;
}
