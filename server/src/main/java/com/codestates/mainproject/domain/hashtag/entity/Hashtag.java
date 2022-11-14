package com.codestates.mainproject.domain.hashtag.entity;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.entity.ArticleHashtag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hashtagId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();


    public void addArticleHashtag(ArticleHashtag articleHashtag) {
        articleHashtags.add(articleHashtag);
    }

}
