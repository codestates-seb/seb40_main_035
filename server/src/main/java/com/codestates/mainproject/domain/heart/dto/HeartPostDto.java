package com.codestates.mainproject.domain.heart.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class HeartPostDto {
    @Positive
    private long memberId;

    @Positive
    private long articleId;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }

    public Article getArticle() {
        Article article = new Article();
        article.setArticleId(articleId);
        return article;
    }
}
