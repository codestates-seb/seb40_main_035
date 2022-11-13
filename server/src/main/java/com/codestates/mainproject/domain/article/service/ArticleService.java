package com.codestates.mainproject.domain.article.service;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.repository.ArticleRepository;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final HashtagService hashtagService;

    public Article createArticle(Article article) {
        Member member = memberService.findVerifiedMember(article.getMemberId());

        article.setMember(member);
        member.addArticle(article);

        return articleRepository.save(article);
    }

    public Article updateArticle(Article article) {
        Article findArticle = findVerifiedArticle(article.getArticleId());

        Optional.ofNullable(article.getTitle())
                .ifPresent(title -> findArticle.setTitle(title));

        Optional.ofNullable(article.getBody())
                .ifPresent(body -> findArticle.setBody(body));

        Optional.ofNullable(article.getIsCompleted())
                .ifPresent(isCompleted -> findArticle.setIsCompleted(isCompleted));

        Optional.ofNullable(article.getStartDay())
                .ifPresent(startDay -> findArticle.setStartDay(startDay));

        Optional.ofNullable(article.getEndDay())
                .ifPresent(endDay -> findArticle.setEndDay(endDay));

        if (article.getBackend() != -1)
            findArticle.setBackend(article.getBackend());

        if (article.getFrontend() != -1)
            findArticle.setFrontend(article.getFrontend());

        return articleRepository.save(findArticle);
    }

    public Article findArticle(long articleId) {
        Article article = findVerifiedArticle(articleId);
        article.addViews();

        return article;
    }

    public Page<Article> findArticles(Boolean status, String sort, int page, int size) {
        if (status != null)
            return articleRepository.findByIsCompleted(status, sortBy(sort, page, size));

        return articleRepository.findAll(sortBy(sort, page, size));
    }

    public void deleteArticle(long articleId) {
        Article article = findArticle(articleId);
        articleRepository.delete(article);
    }

    public Article findVerifiedArticle(long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return optionalArticle.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    private PageRequest sortBy(String sort, int page, int size) {
        switch (sort) {
            case "view": {
                return PageRequest.of(page - 1, size, Sort.by("views").descending().and(Sort.by("articleId").descending()));
            }
            case "heart": {
                return PageRequest.of(page - 1, size, Sort.by("heartCount").descending().and(Sort.by("articleId").descending()));
            }
            case "answer": {
                return PageRequest.of(page - 1, size, Sort.by("answerCount").descending().and(Sort.by("articleId").descending()));
            }
            default: {
                return PageRequest.of(page - 1, size, Sort.by("articleId").descending());
            }
        }
    }
}
