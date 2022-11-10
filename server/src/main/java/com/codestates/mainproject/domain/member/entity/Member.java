package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.converter.StringListConverter;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

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
