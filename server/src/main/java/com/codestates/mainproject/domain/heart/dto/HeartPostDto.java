package com.codestates.mainproject.domain.heart.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
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
