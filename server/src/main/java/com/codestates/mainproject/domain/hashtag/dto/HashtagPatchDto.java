package com.codestates.mainproject.domain.hashtag.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HashtagPatchDto {
    private long hashtagId;

    @NotSpace
    private String name;
}
