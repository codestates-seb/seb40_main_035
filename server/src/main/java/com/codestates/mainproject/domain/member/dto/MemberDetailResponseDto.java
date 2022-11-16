package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.article.dto.ArticleSimpleResponseDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
import com.codestates.mainproject.domain.skill.dto.SkillResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberDetailResponseDto {
    private long memberId;
    private String email;
    private String password;
    private String name;
    private String description;
    private String level;
    private String github;
    private List<InterestResponseDto> interests;
    private List<SkillResponseDto> skills;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ArticleSimpleResponseDto> articles;
    private List<ArticleSimpleResponseDto> heartArticles;
}
