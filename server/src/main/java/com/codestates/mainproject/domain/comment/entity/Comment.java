package com.codestates.mainproject.domain.comment.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false, length = 200)
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
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    public long getAnswerId() {
        return answer.getAnswerId();
    }
}
