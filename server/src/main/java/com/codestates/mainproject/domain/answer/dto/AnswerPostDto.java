package com.codestates.mainproject.domain.answer.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class AnswerPostDto {
    @Positive
    private long articleId;

    @Positive
    private long memberId;

    @NotBlank
    private String body;

    public Article getArticle() {
        Article article = new Article();
        article.setArticleId(articleId);
        return article;
    }

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
