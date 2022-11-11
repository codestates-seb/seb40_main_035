package com.codestates.mainproject.domain.member.entity;

import com.codestates.mainproject.domain.stack.entity.Stack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberStackId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STACK_ID")
    private Stack stack;
}
