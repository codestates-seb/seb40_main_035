package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.domain.industry.entity.Industry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberIndustry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberIndustryId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "INDUSTRY_ID")
    private Industry industry;
}
