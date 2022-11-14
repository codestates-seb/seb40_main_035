package com.codestates.mainproject.domain.comment.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPatchDto {
    private long commentId;

    @NotSpace
    private String body;
}
