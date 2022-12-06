package com.codestates.mainproject.domain.answer.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPatchDto {
    private long answerId;

    @NotSpace
    private String body;
}
