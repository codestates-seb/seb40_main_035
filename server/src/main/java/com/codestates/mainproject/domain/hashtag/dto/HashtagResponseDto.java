package com.codestates.mainproject.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HashtagResponseDto {
    private long hashtagId;
    private String name;
}
