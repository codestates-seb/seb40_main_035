package com.codestates.mainproject.domain.member.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.heart.entity.Heart;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String password;
    private String name;
    private String description;
    private String level;
    private List<String> stack;
    private List<String> field;
    private String github;
    private List<Article> articles;
    private List<Heart> hearts;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
