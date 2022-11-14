package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.interest.entity.Interest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberInterestId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID")
    private Interest interest;

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getMemberInterests().contains(this)) {
            this.member.getMemberInterests().add(this);
        }
    }
}
