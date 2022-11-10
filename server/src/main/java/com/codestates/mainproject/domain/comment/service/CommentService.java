package com.codestates.mainproject.domain.comment.service;

import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.answer.service.AnswerService;
import com.codestates.mainproject.domain.comment.entity.Comment;
import com.codestates.mainproject.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final AnswerService answerService;

    public Comment createComment(Comment comment) {
        Answer answer = answerService.findVerifiedAnswer(comment.getAnswerId());
        Member member = memberService.findVerifiedMember(comment.getMemberId());

        comment.setAnswer(answer);
        comment.setMember(member);

        answer.addComment(comment);
        member.addComment(comment);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getBody())
                .ifPresent(body -> findComment.setBody(body));

        return commentRepository.save(findComment);
    }

    public Comment findComment(long commentId) {
        return findVerifiedComment(commentId);
    }

    public List<Comment> findComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(long commentId) {
        Comment comment = findVerifiedComment(commentId);
        commentRepository.delete(comment);
    }

    private Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
    }
}
