package com.codestates.mainproject.domain.articlehashtag.service;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.domain.articlehashtag.entity.ArticleHashtag;
import com.codestates.mainproject.domain.articlehashtag.repository.ArticleHashtagRepository;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleHashtagService {
    private final ArticleHashtagRepository articleHashtagRepository;
    private final ArticleService articleService;
    private final HashtagService hashtagService;

    public ArticleHashtag createArticleHashtag(ArticleHashtag articleHashtag) {
        Article article = articleService.findVerifiedArticle(articleHashtag.getArticleId());
        Hashtag hashtag = hashtagService.findVerifiedHashtag(articleHashtag.getHashtagId());

        articleHashtag.setArticle(article);
        articleHashtag.setHashtag(hashtag);

        article.addArticleHashtag(articleHashtag);
        hashtag.addArticleHashtag(articleHashtag);

        return articleHashtagRepository.save(articleHashtag);
    }

    public ArticleHashtag findArticleHashtag(long articleHashtagId) {
        return findVerifiedArticleHashtag(articleHashtagId);
    }

    public List<ArticleHashtag> findArticleHashtags() {
        return articleHashtagRepository.findAll();
    }

    public void deleteArticleHashtag(long articleHashtagId) {
        ArticleHashtag articleHashtag = findArticleHashtag(articleHashtagId);
        articleHashtagRepository.delete(articleHashtag);
    }

    public ArticleHashtag findVerifiedArticleHashtag(long articleHashtagId) {
        Optional<ArticleHashtag> optionalArticleHashtag = articleHashtagRepository.findById(articleHashtagId);
        return optionalArticleHashtag.orElseThrow(() -> new RuntimeException());
    }
}
