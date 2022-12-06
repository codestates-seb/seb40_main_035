package com.codestates.mainproject.domain.article.mapper;

import com.codestates.mainproject.domain.article.dto.ArticleDetailResponseDto;
import com.codestates.mainproject.domain.article.dto.ArticlePatchDto;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.article.entity.*;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.skill.entity.Skill;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    default Article articlePostDtoToArticle(ArticlePostDto postDto) {
        Article article = new Article();
        article.setTitle(postDto.getTitle());
        article.setBody(postDto.getBody());
        article.setStartDay(postDto.getStartDay());
        article.setEndDay(postDto.getEndDay());
        article.setBackend(postDto.getBackend());
        article.setFrontend(postDto.getFrontend());
        Member member = postDto.getMember();
        article.setMember(member);

        List<ArticleHashtag> articleHashtags = postDto.getArticleHashtags().stream()
                .map(articleHashtagDto -> {
                    ArticleHashtag articleHashtag = new ArticleHashtag();
                    Hashtag hashtag = new Hashtag();
                    hashtag.setName(articleHashtagDto.getHashtagName());
                    articleHashtag.setHashtag(hashtag);
                    articleHashtag.addArticle(article);
                    return articleHashtag;
                })
                .collect(Collectors.toList());

        article.setArticleHashtags(articleHashtags);

        List<ArticleInterest> articleInterests = postDto.getArticleInterests().stream()
                .map(articleInterestDto -> {
                    ArticleInterest articleInterest = new ArticleInterest();
                    Interest interest = new Interest();
                    interest.setName(articleInterestDto.getInterestName());
                    articleInterest.setInterest(interest);
                    articleInterest.addArticle(article);
                    return articleInterest;
                })
                .collect(Collectors.toList());

        article.setArticleInterests(articleInterests);

        List<ArticleSkill> articleSkills = postDto.getArticleSkills().stream()
                .map(articleSkillDto -> {
                    ArticleSkill articleSkill = new ArticleSkill();
                    Skill skill = new Skill();
                    skill.setName(articleSkillDto.getSkillName());
                    articleSkill.setSkill(skill);
                    articleSkill.addArticle(article);
                    return articleSkill;
                })
                .collect(Collectors.toList());

        article.setArticleSkills(articleSkills);

        return article;
    }

    default Article articlePatchDtoToArticle(ArticlePatchDto patchDto) {
        Article article = new Article();
        article.setArticleId(patchDto.getArticleId());
        article.setTitle(patchDto.getTitle());
        article.setBody(patchDto.getBody());
        article.setIsCompleted(patchDto.getIsCompleted());
        article.setStartDay(patchDto.getStartDay());
        article.setEndDay(patchDto.getEndDay());
        article.setBackend(patchDto.getBackend());
        article.setFrontend(patchDto.getFrontend());

        if (patchDto.getHearts() != null) {
            List<Heart> hearts = patchDto.getHearts().stream()
                    .map(heartDto -> {
                        Heart heart = new Heart();
                        Member member = new Member();
                        member.setMemberId(heartDto.getMemberId());
                        heart.setMember(member);
                        heart.setArticle(article);
                        return heart;
                    })
                    .collect(Collectors.toList());

            article.setHearts(hearts);
        }

        if (patchDto.getArticleHashtags() != null) {
            List<ArticleHashtag> articleHashtags = patchDto.getArticleHashtags().stream()
                    .map(articleHashtagDto -> {
                        ArticleHashtag articleHashtag = new ArticleHashtag();
                        Hashtag hashtag = new Hashtag();
                        hashtag.setName(articleHashtagDto.getHashtagName());
                        articleHashtag.setHashtag(hashtag);
                        articleHashtag.setArticle(article);
                        return articleHashtag;
                    })
                    .collect(Collectors.toList());

            article.setArticleHashtags(articleHashtags);
        }

        if (patchDto.getArticleInterests() != null) {
            List<ArticleInterest> articleInterests = patchDto.getArticleInterests().stream()
                    .map(articleInterestDto -> {
                        ArticleInterest articleInterest = new ArticleInterest();
                        Interest interest = new Interest();
                        interest.setName(articleInterestDto.getInterestName());
                        articleInterest.setInterest(interest);
                        articleInterest.setArticle(article);
                        return articleInterest;
                    })
                    .collect(Collectors.toList());

            article.setArticleInterests(articleInterests);
        }

        if (patchDto.getArticleSkills() != null) {
            List<ArticleSkill> articleSkills = patchDto.getArticleSkills().stream()
                    .map(articleSkillDto -> {
                        ArticleSkill articleSkill = new ArticleSkill();
                        Skill skill = new Skill();
                        skill.setName(articleSkillDto.getSkillName());
                        articleSkill.setSkill(skill);
                        articleSkill.setArticle(article);
                        return articleSkill;
                    })
                    .collect(Collectors.toList());

            article.setArticleSkills(articleSkills);
        }

        return article;
    }

    ArticleDetailResponseDto articleToArticleDetailResponseDto(Article article);

    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articlesToArticleResponseDtos(List<Article> articles);
}
