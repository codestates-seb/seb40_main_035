package com.codestates.mainproject.domain.article.mapper;

import com.codestates.mainproject.domain.article.dto.ArticleDetailResponseDto;
import com.codestates.mainproject.domain.article.dto.ArticlePatchDto;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.entity.ArticleHashtag;
import com.codestates.mainproject.domain.article.entity.Heart;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    default Article articlePostDtoToArticle(ArticlePostDto postDto) {
        Article article = new Article();
        article.setTitle(postDto.getTitle());
        article.setBody(postDto.getBody());
        article.setStartDay(postDto.getStartDay());
        article.setEndDay(postDto.getEndDay());
        article.setBackend(postDto.getBackend());
        article.setFrontend(postDto.getFrontend());
        Member member = postDto.getMember();
        article.setMember(member);

        List<ArticleHashtag> articleHashtags = postDto.getArticleHashtags().stream()
                .map(articleHashtagDto -> {
                    ArticleHashtag articleHashtag = new ArticleHashtag();
                    Hashtag hashtag = new Hashtag();
                    hashtag.setName(articleHashtagDto.getHashtagName());
                    articleHashtag.setHashtag(hashtag);
                    articleHashtag.addArticle(article);
                    return articleHashtag;
                })
                .collect(Collectors.toList());

        article.setArticleHashtags(articleHashtags);

        return article;
    }

    default Article articlePatchDtoToArticle(ArticlePatchDto patchDto) {
        Article article = new Article();
        article.setArticleId(patchDto.getArticleId());
        article.setTitle(patchDto.getTitle());
        article.setBody(patchDto.getBody());
        article.setIsCompleted(patchDto.getIsCompleted());
        article.setStartDay(patchDto.getStartDay());
        article.setEndDay(patchDto.getEndDay());
        article.setBackend(patchDto.getBackend());
        article.setFrontend(patchDto.getFrontend());

        if (patchDto.getHearts() != null) {
            List<Heart> hearts = patchDto.getHearts().stream()
                    .map(heartDto -> {
                        Heart heart = new Heart();
                        Member member = new Member();
                        member.setMemberId(heartDto.getMemberId());
                        heart.setMember(member);
                        heart.setArticle(article);
                        return heart;
                    })
                    .collect(Collectors.toList());

            article.setHearts(hearts);
        }

        if (patchDto.getArticleHashtags() != null) {
            List<ArticleHashtag> articleHashtags = patchDto.getArticleHashtags().stream()
                    .map(articleHashtagDto -> {
                        ArticleHashtag articleHashtag = new ArticleHashtag();
                        Hashtag hashtag = new Hashtag();
                        hashtag.setName(articleHashtagDto.getHashtagName());
                        articleHashtag.setHashtag(hashtag);
                        articleHashtag.setArticle(article);
                        return articleHashtag;
                    })
                    .collect(Collectors.toList());

            article.setArticleHashtags(articleHashtags);
        }

        return article;
    }

    ArticleDetailResponseDto articleToArticleDetailResponseDto(Article article);

    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articlesToArticleResponseDtos(List<Article> articles);
}
