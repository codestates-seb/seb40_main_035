package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.article.dto.ArticleSimpleResponseDto;
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
    private List<String> stack;
    private List<String> field;
    private String github;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ArticleSimpleResponseDto> articles;
    private List<ArticleSimpleResponseDto> heartArticles;
}
