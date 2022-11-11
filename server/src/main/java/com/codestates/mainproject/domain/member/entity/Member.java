package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.converter.StringListConverter;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.article.dto.ArticleSimpleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.comment.entity.Comment;
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
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passwordCheck;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description = "";

    @Column(nullable = false)
    private String level = "";

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> stack = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> field = new ArrayList<>();

    @Column(nullable = false)
    private String github = "";

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    public List<Article> getHeartArticles() {
        return hearts.stream()
                .map(heart -> heart.getArticle())
                .collect(Collectors.toList());
    }

    public void addHeart(Heart heart) {
        hearts.add(heart);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
