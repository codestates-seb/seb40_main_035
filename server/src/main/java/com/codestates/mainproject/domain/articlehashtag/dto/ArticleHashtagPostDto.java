package com.codestates.mainproject.domain.articlehashtag.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class ArticleHashtagPostDto {
    @Positive
    private long articleId;

    @Positive
    private long hashtagId;

    public Article getArticle() {
        Article article = new Article();
        article.setArticleId(articleId);
        return article;
    }

    public Hashtag getHashtag() {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagId(hashtagId);
        return hashtag;
    }
}
