package com.codestates.mainproject.domain.article.repository;

import com.codestates.mainproject.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByIsCompleted(Boolean isCompleted, Pageable pageable);
}
