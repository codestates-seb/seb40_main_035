package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleInterestId;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID")
    private Interest interest;

    public void addArticle(Article article) {
        this.article = article;
        if (!this.article.getArticleInterests().contains(this)) {
            this.article.getArticleInterests().add(this);
        }
    }
}
