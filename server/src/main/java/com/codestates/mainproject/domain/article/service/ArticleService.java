package com.codestates.mainproject.domain.article.service;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.repository.ArticleRepository;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

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

        Optional.ofNullable(article.getField())
                .ifPresent(field -> {
                    if (!field.isEmpty()) {
                        findArticle.setField(field);
                    }
                });

        return articleRepository.save(findArticle);
    }

    public Article findArticle(long articleId) {
        Article article = findVerifiedArticle(articleId);
        article.addViews();

        return article;
    }

    public Page<Article> findArticles(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("articleId").descending());
        return articleRepository.findAll(pageRequest);
    }

    public void deleteArticle(long articleId) {
        Article article = findArticle(articleId);
        articleRepository.delete(article);
    }

    public Article findVerifiedArticle(long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return optionalArticle.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }
}
