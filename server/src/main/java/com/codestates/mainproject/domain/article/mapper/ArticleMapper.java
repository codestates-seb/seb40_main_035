package com.codestates.mainproject.domain.article.mapper;

import com.codestates.mainproject.domain.article.dto.ArticleDetailResponseDto;
import com.codestates.mainproject.domain.article.dto.ArticlePatchDto;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article articlePostDtoToArticle(ArticlePostDto postDto);

    Article articlePatchDtoToArticle(ArticlePatchDto patchDto);

    ArticleDetailResponseDto articleToArticleDetailResponseDto(Article article);

    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articlesToArticleResponseDtos(List<Article> articles);
}
