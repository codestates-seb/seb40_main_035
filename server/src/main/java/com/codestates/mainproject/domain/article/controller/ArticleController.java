package com.codestates.mainproject.domain.article.controller;

import com.codestates.mainproject.domain.article.dto.ArticlePatchDto;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.mapper.ArticleMapper;
import com.codestates.mainproject.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/articles")
@Validated
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper mapper;

    @PostMapping
    public ResponseEntity postArticle(@Valid @RequestBody ArticlePostDto postDto) {

        return null;
    }

    @PatchMapping("/{article-id}")
    public ResponseEntity patchArticle(@PathVariable("article-id") @Positive long articleId,
                                       @Valid @RequestBody ArticlePatchDto patchDto) {

        return null;
    }

    @GetMapping("/{article-id}")
    public ResponseEntity getArticle(@PathVariable("article-id") @Positive long articleId) {

        return null;
    }

    @GetMapping
    public ResponseEntity getArticles(@RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {

        return null;
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity deleteArticle(@PathVariable("article-id") @Positive long articleId) {

        return null;
    }
}
