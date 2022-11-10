package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
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

    @Nullable
    private List<@NotBlank String> stack;

    @Nullable
    private List<@NotBlank String> field;

    @NotSpace
    private String github;
}
