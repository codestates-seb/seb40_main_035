package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.entity.Heart;
import com.codestates.mainproject.domain.comment.entity.Comment;


import lombok.Builder;

import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.skill.entity.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 80)
    private String passwordCheck;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(nullable = false)
    private String description = "";

    @Column(nullable = false, length = 20)
    private String level;

    @Column(nullable = false, length = 50)
    private String github;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles.stream()
                .sorted(Comparator.comparing(Article::getArticleId).reversed())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    public List<Article> getHeartArticles() {
        return hearts.stream()
                .map(heart -> heart.getArticle())
                .sorted(Comparator.comparing(Article::getArticleId).reversed())
                .collect(Collectors.toList());
    }

    public void addHeart(Heart heart) {
        hearts.add(heart);
    }

    public void removeHeart(Heart heart) {
        hearts.remove(heart);
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberInterest> memberInterests = new ArrayList<>();

    public void addMemberInterest(MemberInterest memberInterest) {
        memberInterests.add(memberInterest);
    }

    public List<Interest> getInterests() {
        return memberInterests.stream()
                .map(memberInterest -> memberInterest.getInterest())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberSkill> memberSkills = new ArrayList<>();

    public void addMemberSkill(MemberSkill memberSkill) {
        memberSkills.add(memberSkill);
    }

    public List<Skill> getSkills() {
        return memberSkills.stream()
                .map(memberSkill -> memberSkill.getSkill())
                .collect(Collectors.toList());
    }

    public Member(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
