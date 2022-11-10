package com.codestates.mainproject.domain.articlehashtag.mapper;

import com.codestates.mainproject.domain.articlehashtag.dto.ArticleHashtagPostDto;
import com.codestates.mainproject.domain.articlehashtag.dto.ArticleHashtagResponseDto;
import com.codestates.mainproject.domain.articlehashtag.entity.ArticleHashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleHashtagMapper {
    ArticleHashtag articleHashtagPostDtoToArticleHashtag(ArticleHashtagPostDto postDto);

    ArticleHashtagResponseDto articleHashtagToArticleHashtagResponseDto(ArticleHashtag articleHashtag);

    List<ArticleHashtagResponseDto> articleHashtagsToArticleHashtagResponseDtos(List<ArticleHashtag> articleHashtags);
}
