package com.codestates.mainproject.domain.heart.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Heart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long heartId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public long getMemberId() {
        return member.getMemberId();
    }

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    public long getArticleId() {
        return article.getArticleId();
    }
}
