package com.codestates.mainproject.domain.article.service;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.entity.ArticleHashtag;
import com.codestates.mainproject.domain.article.entity.Heart;
import com.codestates.mainproject.domain.article.repository.ArticleRepository;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.interest.service.InterestService;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.domain.skill.service.SkillService;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
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
    private final InterestService interestService;
    private final SkillService skillService;

    public Article createArticle(Article article) {
        Member member = memberService.findVerifiedMember(article.getMemberId());

        article.setMember(member);

        article.getArticleHashtags().stream()
                .forEach(articleHashtag -> {
                    Hashtag hashtag = hashtagService.findVerifiedHashtag(articleHashtag.getHashtag().getName());
                    articleHashtag.setHashtag(hashtag);
                    hashtag.addArticleHashtag(articleHashtag);
                });

        article.getArticleInterests().stream()
                .forEach(articleInterest -> {
                    Interest interest = interestService.findVerifiedInterest(articleInterest.getInterest().getName());
                    articleInterest.setInterest(interest);
                    interest.addArticleInterest(articleInterest);
                });

        article.getArticleSkills().stream()
                .forEach(articleSkill -> {
                    Skill skill = skillService.findVerifiedSkill(articleSkill.getSkill().getName());
                    articleSkill.setSkill(skill);
                    skill.addArticleSkill(articleSkill);
                });

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

        Optional.ofNullable(article.getHearts())
                .ifPresent(hearts -> {
                    if (!hearts.isEmpty()) {
                        hearts.stream()
                                .forEach(heart -> {
                                    Member member = memberService.findVerifiedMember(heart.getMember().getMemberId());
                                    heart.setArticle(findArticle);
                                    heart.setMember(member);

                                    Optional<Heart> optionalHeart = member.getHearts().stream()
                                            .filter(heart1 -> heart1.getArticle() == findArticle)
                                            .findAny();

                                    if (optionalHeart.isPresent()) {
                                        findArticle.removeHeart(optionalHeart.get());
//                                        member.removeHeart(optionalHeart.get());
//                                        findArticle.removeHeart(optionalHeart.get())에서 heart 객체는 참조를 잃으면서 제거됨(orphanRemoval = true)
                                    } else {
                                        findArticle.addHeart(heart);
                                        member.addHeart(heart);
                                    }
                                });
                    }
                });

        Optional.ofNullable(article.getArticleHashtags())
                .ifPresent(articleHashtags -> {
                    if (!articleHashtags.isEmpty()) {
                        findArticle.getArticleHashtags().clear();
                        articleHashtags.stream()
                                .forEach(articleHashtag -> {
                                    Hashtag hashtag = hashtagService.findVerifiedHashtag(articleHashtag.getHashtag().getName());
                                    articleHashtag.setArticle(findArticle);
                                    articleHashtag.setHashtag(hashtag);

                                    findArticle.addArticleHashtag(articleHashtag);
                                    hashtag.addArticleHashtag(articleHashtag);
                                });
                    }
                });

        Optional.ofNullable(article.getArticleInterests())
                .ifPresent(articleInterests -> {
                    if (!articleInterests.isEmpty()) {
                        findArticle.getArticleInterests().clear();
                        articleInterests.stream()
                                .forEach(articleInterest -> {
                                    Interest interest = interestService.findVerifiedInterest(articleInterest.getInterest().getName());
                                    articleInterest.setArticle(findArticle);
                                    articleInterest.setInterest(interest);

                                    findArticle.addArticleInterest(articleInterest);
                                    interest.addArticleInterest(articleInterest);
                                });
                    }
                });

        Optional.ofNullable(article.getArticleSkills())
                .ifPresent(articleSkills -> {
                    if (!articleSkills.isEmpty()) {
                        findArticle.getArticleSkills().clear();
                        articleSkills.stream()
                                .forEach(articleSkill -> {
                                    Skill skill = skillService.findVerifiedSkill(articleSkill.getSkill().getName());
                                    articleSkill.setArticle(findArticle);
                                    articleSkill.setSkill(skill);

                                    findArticle.addArticleSkill(articleSkill);
                                    skill.addArticleSkill(articleSkill);
                                });
                    }
                });

        return articleRepository.save(findArticle);
    }

    public Article findArticle(long articleId) {
        Article article = findVerifiedArticle(articleId);
        article.addViews();
        setCount(article);

        return article;
    }

    public Page<Article> findArticles(Boolean status, String sort, int page, int size) {
        if (status != null) {
            Page<Article> articlePage = articleRepository.findByIsCompleted(status, sortBy(sort, page, size));
            articlePage.getContent().stream().forEach(article -> setCount(article));
            return articlePage;
        }

        Page<Article> articlePage =  articleRepository.findAll(sortBy(sort, page, size));
        articlePage.getContent().stream().forEach(article -> setCount(article));
        return articlePage;
    }

    public void deleteArticle(long articleId) {
        Article article = findArticle(articleId);
        articleRepository.delete(article);
    }

    public Article findVerifiedArticle(long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return optionalArticle.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
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

    private void setCount(Article article) {
        if (article.getAnswerCount() != article.getAnswers().size()) {
            article.setAnswerCount(article.getAnswers().size());
        }
        if (article.getHeartCount() != article.getHearts().size()) {
            article.setHeartCount(article.getHearts().size());
        }
    }

}
