package com.codestates.mainproject.domain.interest.entity;

import com.codestates.mainproject.domain.article.entity.ArticleInterest;
import com.codestates.mainproject.domain.member.entity.MemberInterest;
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
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interestId;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @OneToMany(mappedBy = "interest", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberInterest> memberInterests = new ArrayList<>();

    public void addMemberInterest(MemberInterest memberInterest) {
        memberInterests.add(memberInterest);
    }

    @OneToMany(mappedBy = "interest", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleInterest> articleInterests = new ArrayList<>();

    public void addArticleInterest(ArticleInterest articleInterest) {
        articleInterests.add(articleInterest);
    }
}
