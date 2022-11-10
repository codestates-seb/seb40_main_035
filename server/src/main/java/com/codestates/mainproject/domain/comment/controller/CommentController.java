package com.codestates.mainproject.domain.comment.controller;

import com.codestates.mainproject.domain.comment.dto.CommentPatchDto;
import com.codestates.mainproject.domain.comment.dto.CommentPostDto;
import com.codestates.mainproject.domain.comment.dto.CommentResponseDto;
import com.codestates.mainproject.domain.comment.entity.Comment;
import com.codestates.mainproject.domain.comment.mapper.CommentMapper;
import com.codestates.mainproject.domain.comment.service.CommentService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/comments")
@Validated
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper mapper;

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentPostDto postDto) {

        Comment comment = mapper.commentPostDtoToComment(postDto);
        Comment createdComment = commentService.createComment(comment);
        CommentResponseDto responseDto = mapper.commentToCommentResponseDto(createdComment);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentPatchDto patchDto) {
        patchDto.setCommentId(commentId);

        Comment comment = mapper.commentPatchDtoToComment(patchDto);
        Comment updatedComment = commentService.updateComment(comment);
        CommentResponseDto responseDto = mapper.commentToCommentResponseDto(updatedComment);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{comment-id}")
    public ResponseEntity getComment(@PathVariable("comment-id") @Positive long commentId) {

        Comment findComment = commentService.findComment(commentId);
        CommentResponseDto responseDto = mapper.commentToCommentResponseDto(findComment);

        return new ResponseEntity(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getComments() {

        List<Comment> comments = commentService.findComments();
        List<CommentResponseDto> responseDtos = mapper.commentsToCommentResponseDtos(comments);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId) {

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}