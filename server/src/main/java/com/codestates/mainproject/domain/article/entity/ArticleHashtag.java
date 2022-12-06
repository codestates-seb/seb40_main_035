package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleHashtagId;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "HASHTAG_ID")
    private Hashtag hashtag;

    public void addArticle(Article article) {
        this.article = article;
        if (!this.article.getArticleHashtags().contains(this)) {
            this.article.getArticleHashtags().add(this);
        }
    }

    public void addHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
        if (!this.hashtag.getArticleHashtags().contains(this)) {
            this.hashtag.addArticleHashtag(this);
        }
    }
}
