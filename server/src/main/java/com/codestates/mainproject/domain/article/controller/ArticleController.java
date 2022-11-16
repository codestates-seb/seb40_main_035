package com.codestates.mainproject.domain.article.controller;

import com.codestates.mainproject.domain.article.dto.ArticleDetailResponseDto;
import com.codestates.mainproject.domain.article.dto.ArticlePatchDto;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.mapper.ArticleMapper;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.dto.MultiResponseDto;
import com.codestates.mainproject.dto.PageInfo;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/articles")
@Validated
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper mapper;

    @PostMapping
    public ResponseEntity postArticle(@Valid @RequestBody ArticlePostDto postDto) {

        Article article = mapper.articlePostDtoToArticle(postDto);
        Article createdArticle = articleService.createArticle(article);
        ArticleDetailResponseDto responseDto = mapper.articleToArticleDetailResponseDto(createdArticle);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{article-id}")
    public ResponseEntity patchArticle(@PathVariable("article-id") @Positive long articleId,
                                       @Valid @RequestBody ArticlePatchDto patchDto) {
        patchDto.setArticleId(articleId);

        Article article = mapper.articlePatchDtoToArticle(patchDto);
        Article updatedArticle = articleService.updateArticle(article);
        ArticleDetailResponseDto responseDto = mapper.articleToArticleDetailResponseDto(updatedArticle);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity getArticle(@PathVariable("article-id") @Positive long articleId) {

        Article foundArticle = articleService.findArticle(articleId);
        ArticleDetailResponseDto responseDto = mapper.articleToArticleDetailResponseDto(foundArticle);

        return new ResponseEntity(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getArticles(@RequestParam(value = "skill", required = false) List<@NotBlank String> skill,
                                      @RequestParam(value = "status", required = false) Boolean status,
                                      @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
                                      @RequestParam("page") @Positive int page,
                                      @RequestParam("size") @Positive int size) {

        Page<Article> articlePage = articleService.findArticles(skill, status, sort, page, size);
        List<Article> articles = articlePage.getContent();
        List<ArticleResponseDto> responseDtos = mapper.articlesToArticleResponseDtos(articles);
        PageInfo pageInfo = new PageInfo(articlePage.getNumber() + 1, articlePage.getSize(), articlePage.getTotalElements(), articlePage.getTotalPages());

        return new ResponseEntity(new MultiResponseDto<>(responseDtos, pageInfo), HttpStatus.OK);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity deleteArticle(@PathVariable("article-id") @Positive long articleId) {

        articleService.deleteArticle(articleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
