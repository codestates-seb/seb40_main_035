package com.codestates.mainproject.domain.articlehashtag.repository;

import com.codestates.mainproject.domain.articlehashtag.entity.ArticleHashtag;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtag, Long> {
}
