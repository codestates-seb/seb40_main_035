package com.codestates.mainproject.domain.stack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class StackPostDto {
    private long stackId;
    @NotBlank
    private String name;
}
