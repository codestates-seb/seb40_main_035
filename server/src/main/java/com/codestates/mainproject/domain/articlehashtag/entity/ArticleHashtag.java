package com.codestates.mainproject.domain.articlehashtag.entity;

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

    public long getArticleId() {
        return article.getArticleId();
    }

    @ManyToOne
    @JoinColumn(name = "HASHTAG_ID")
    private Hashtag hashtag;

    public long getHashtagId() {
        return hashtag.getHashtagId();
    }
}
