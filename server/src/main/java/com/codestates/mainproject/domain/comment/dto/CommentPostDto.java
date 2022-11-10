package com.codestates.mainproject.domain.comment.dto;

import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class CommentPostDto {
    @Positive
    private long memberId;

    @Positive
    private long answerId;

    @NotBlank
    private String body;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }

    public Answer getAnswer() {
        Answer answer = new Answer();
        answer.setAnswerId(answerId);
        return answer;
    }
}
