package com.codestates.mainproject.domain.heart.service;

import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.heart.repository.HeartRepository;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberService memberService;
    private final ArticleService articleService;

//    public Heart createHeart(Heart heart) {
//        Article article = articleService.findverifiedArticle(heart.getArticleId());
//        Member member = memberService.findVerifiedMember(heart.getMemberId());
//
//        heart.setArticle(article);
//        heart.setMember(member);
//
//        article.addHeart(heart);
//        member.addHeart(heart);
//
//        return heartRepository.save(heart);
//    }

    public void deleteHeart(long heartId) {
        Heart heart = findVerifiedHeart(heartId);
        heartRepository.delete(heart);
    }

    public Heart findVerifiedHeart(long heartId) {
        Optional<Heart> optionalHeart = heartRepository.findById(heartId);
        return optionalHeart.orElseThrow(() -> new RuntimeException("존재하지 않는 '좋아요'입니다."));
    }
}
