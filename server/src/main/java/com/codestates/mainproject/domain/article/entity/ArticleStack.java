package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.domain.stack.entity.Stack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleStackId;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "STACK_ID")
    private Stack stack;
}
