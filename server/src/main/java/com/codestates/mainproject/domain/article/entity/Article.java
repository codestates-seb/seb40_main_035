package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.category.entity.Category;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.member.entity.Member;
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
public class Article extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String body;

    @Column(nullable = false)
    private long views;

    @Column(nullable = false)
    private Boolean isCompleted = false;

    @Column(nullable = false)
    private String startDay;

    @Column(nullable = false)
    private String endDay;

    @Column(nullable = false)
    private int backend;

    @Column(nullable = false)
    private int frontend;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
