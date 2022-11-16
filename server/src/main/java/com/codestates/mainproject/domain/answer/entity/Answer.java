package com.codestates.mainproject.domain.answer.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.comment.entity.Comment;
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

public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(nullable = false, length = 5000)
    private String body;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public long getMemberId() {
        return member.getMemberId();
    }

    public String getMemberName() {
        return member.getName();
    }

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    public long getArticleId() {
        return article.getArticleId();
    }

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
