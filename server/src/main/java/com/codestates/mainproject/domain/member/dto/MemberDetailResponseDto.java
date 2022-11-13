package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.article.dto.ArticleSimpleResponseDto;
import com.codestates.mainproject.domain.industry.dto.IndustryResponseDto;
import com.codestates.mainproject.domain.stack.dto.StackResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MemberDetailResponseDto {
    private long memberId;
    private String email;
    private String password;
    private String name;
    private String description;
    private String level;
    private String github;
    private List<IndustryResponseDto> industries;
    private List<StackResponseDto> stacks;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ArticleSimpleResponseDto> articles;
    private List<ArticleSimpleResponseDto> heartArticles;
}
