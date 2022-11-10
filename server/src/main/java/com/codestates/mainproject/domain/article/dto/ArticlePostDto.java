package com.codestates.mainproject.domain.article.dto;

import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.article.entity.ArticleHashtag;
import com.codestates.mainproject.domain.category.entity.Category;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ArticlePostDto {
    private String title;
    private String body;
    private long views;
    private Boolean isCompleted = false;
    private String startDay;
    private String endDay;
    private int backend;
    private int frontend;
    private List<String> field = new ArrayList<>();
    private Member member;
    private Category category;
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();
    private List<Heart> hearts = new ArrayList<>();
    private List<Answer> answers = new ArrayList<>();
}
