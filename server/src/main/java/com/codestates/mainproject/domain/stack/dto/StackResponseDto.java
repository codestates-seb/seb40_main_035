package com.codestates.mainproject.domain.stack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StackResponseDto {
    private long stackId;
    private String name;
}
