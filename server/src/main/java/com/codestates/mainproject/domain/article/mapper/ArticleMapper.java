package com.codestates.mainproject.domain.article.mapper;

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

    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articlesToArticleResponseDtos(List<Article> articles);
}
