package com.codestates.mainproject.domain.articlehashtag.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleHashtagResponseDto {
    private long articleHashtagId;
    private long articleId;
    private long hashtagId;
}
