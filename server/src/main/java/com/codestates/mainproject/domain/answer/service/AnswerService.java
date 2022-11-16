package com.codestates.mainproject.domain.answer.service;

import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.answer.repository.AnswerRepository;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ArticleService articleService;
    private final MemberService memberService;

    public Answer createAnswer(Answer answer) {
        Article article = articleService.findVerifiedArticle(answer.getArticleId());
        Member member = memberService.findVerifiedMember(answer.getMemberId());

        answer.setArticle(article);
        answer.setMember(member);

        article.addAnswer(answer);
        member.addAnswer(answer);

        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getBody())
                .ifPresent(body -> findAnswer.setBody(body));

        return answerRepository.save(findAnswer);
    }

    public Answer findAnswer(long answerId) {
        return findVerifiedAnswer(answerId);
    }

    public List<Answer> findAnswers() {
        return answerRepository.findAll();
    }

    public void deleteAnswer(long answerId) {
        Answer answer = findVerifiedAnswer(answerId);
        answerRepository.delete(answer);
    }

    public Answer findVerifiedAnswer(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        return optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
