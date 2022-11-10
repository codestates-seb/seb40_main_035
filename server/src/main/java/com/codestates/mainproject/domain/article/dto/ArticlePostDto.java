package com.codestates.mainproject.domain.article.dto;

import com.codestates.mainproject.domain.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class ArticlePostDto {
    @Positive
    private long memberId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String startDay;

    @NotBlank
    private String endDay;

    @Min(0)
    private int backend;

    @Min(0)
    private int frontend;

    @NotEmpty
    private List<@NotBlank String> field;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}