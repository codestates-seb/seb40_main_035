package com.codestates.mainproject.domain.articlehashtag.controller;

import com.codestates.mainproject.domain.articlehashtag.dto.ArticleHashtagPostDto;
import com.codestates.mainproject.domain.articlehashtag.dto.ArticleHashtagResponseDto;
import com.codestates.mainproject.domain.articlehashtag.entity.ArticleHashtag;
import com.codestates.mainproject.domain.articlehashtag.mapper.ArticleHashtagMapper;
import com.codestates.mainproject.domain.articlehashtag.service.ArticleHashtagService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/article-hashtag")
@Validated
@RequiredArgsConstructor
public class ArticleHashtagController {
    private final ArticleHashtagService articleHashtagService;
    private final ArticleHashtagMapper mapper;

    @PostMapping
    public ResponseEntity postArticleHashtag(ArticleHashtagPostDto postDto) {

        ArticleHashtag articleHashtag = mapper.articleHashtagPostDtoToArticleHashtag(postDto);
        ArticleHashtag createdArticleHashtag = articleHashtagService.createArticleHashtag(articleHashtag);
        ArticleHashtagResponseDto responseDto = mapper.articleHashtagToArticleHashtagResponseDto(createdArticleHashtag);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{article-hashtag-id}")
    public ResponseEntity getArticleHashtag(@PathVariable("article-hashtag-id") @Positive long articleHashtagId) {

        ArticleHashtag foundArticleHashtag = articleHashtagService.findArticleHashtag(articleHashtagId);
        ArticleHashtagResponseDto responseDto = mapper.articleHashtagToArticleHashtagResponseDto(foundArticleHashtag);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getArticleHashtags() {

        List<ArticleHashtag> articleHashtags = articleHashtagService.findArticleHashtags();
        List<ArticleHashtagResponseDto> responseDtos = mapper.articleHashtagsToArticleHashtagResponseDtos(articleHashtags);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{article-hashtag-id}")
    public ResponseEntity deleteArticleHashtag(@PathVariable("article-hashtag-id") @Positive long articleHashtagId) {

        articleHashtagService.deleteArticleHashtag(articleHashtagId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
