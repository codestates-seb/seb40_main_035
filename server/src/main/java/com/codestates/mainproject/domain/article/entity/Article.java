package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.skill.entity.Skill;
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
public class Article extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 2000)
    private String body;

    @Column(nullable = false)
    private long views;

    public void addViews() {
        views++;
    }

    @Column(nullable = false)
    private Boolean isCompleted = false;

    @Column(nullable = false, length = 30)
    private String startDay;

    @Column(nullable = false, length = 30)
    private String endDay;

    @Column(nullable = false)
    private int backend;

    @Column(nullable = false)
    private int frontend;

    @Column(nullable = false)
    private int heartCount;

    @Column(nullable = false)
    private int answerCount;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public long getMemberId() {
        return member.getMemberId();
    }

    public String getMemberName() {
        return member.getName();
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();

    public void addArticleHashtag(ArticleHashtag articleHashtag) {
        articleHashtags.add(articleHashtag);
    }

    public List<Hashtag> getHashtags() {
        return articleHashtags.stream()
                .map(articleHashtag -> articleHashtag.getHashtag())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    public void addHeart(Heart heart) {
        hearts.add(heart);
        heartCount++;
    }

    public void removeHeart(Heart heart) {
        hearts.remove(heart);
        heartCount--;
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answerCount++;
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleInterest> articleInterests = new ArrayList<>();

    public void addArticleInterest(ArticleInterest articleInterest) {
        articleInterests.add(articleInterest);
    }

    public List<Interest> getInterests() {
        return articleInterests.stream()
                .map(articleInterest -> articleInterest.getInterest())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleSkill> articleSkills = new ArrayList<>();

    public void addArticleSkill(ArticleSkill articleSkill) {
        articleSkills.add(articleSkill);
    }

    public List<Skill> getSkills() {
        return articleSkills.stream()
                .map(articleSkill -> articleSkill.getSkill())
                .collect(Collectors.toList());
    }
}
