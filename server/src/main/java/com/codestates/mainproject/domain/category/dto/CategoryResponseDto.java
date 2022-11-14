package com.codestates.mainproject.domain.category.dto;

import com.codestates.mainproject.domain.article.entity.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CategoryResponseDto {
    private long categoryId;
    private String name;
    private List<Article> articles;
}
