package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.converter.StringListConverter;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.articlehashtag.entity.ArticleHashtag;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.member.entity.Member;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String body;

    @Column(nullable = false)
    private long views;

    public void addViews() {
        views++;
    }

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

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> field = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public long getMemberId() {
        return member.getMemberId();
    }

    public String getMemberName() {
        return member.getName();
    }

    @OneToMany(mappedBy = "article")
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();

    public void addArticleHashtag(ArticleHashtag articleHashtag) {
        articleHashtags.add(articleHashtag);
    }

    public List<Hashtag> getHashtags() {
        return articleHashtags.stream()
                .map(articleHashtag -> articleHashtag.getHashtag())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "article")
    private List<Heart> hearts = new ArrayList<>();

    public int getHeartCount() {
        return hearts.size();
    }

    public void addHeart(Heart heart) {
        hearts.add(heart);
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
