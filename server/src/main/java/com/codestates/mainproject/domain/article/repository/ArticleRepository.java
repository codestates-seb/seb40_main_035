package com.codestates.mainproject.domain.article.repository;

import com.codestates.mainproject.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
