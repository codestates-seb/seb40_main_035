package com.codestates.mainproject.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    //400 Bad Request
    FIELD_ERROR(400,"FIELD ERROR"),
    CONSTRAINT_VIOLATION_ERROR(400, "CONSTRAINT VIOLATION ERROR"),
    BAD_PARAMETER_ERROR(400,"동일한 비밀번호를 입력하세요."),

    //401 Unauthorized

    //403 Forbidden

    //404 Not Found
    MEMBER_NOT_FOUND(404, "회원이 존재하지 않습니다."),
    ARTICLE_NOT_FOUND(404, "게시글이 존재하지 않습니다."),
    ANSWER_NOT_FOUND(404, "답변이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(404, "댓글이 존재하지 않습니다."),
    HASHTAG_NOT_FOUND(404,"태그가 존재하지 않습니다."),
    INTEREST_NOT_FOUND(404, "관심분야가 존재하지 않습니다."),
    SKILL_NOT_FOUND(404, "기술스택이 존재하지 않습니다."),

    //409 Conflict
    MEMBER_ALREADY_EXISTS(409, "이미 존재하는 회원입니다"),
    MEMBER_NAME_ALREADY_EXISTS(409, "이미 존재하는 회원 이름입니다"),
    MEMBER_EMAIL_ALREADY_EXISTS(409, "이미 존재하는 회원 이메일입니다."),
    HASHTAG_ALREADY_EXISTS(409, "이미 존재하는 태그입니다."),
    INTEREST_ALREADY_EXISTS(409, "이미 존재하는 관심분야입니다."),
    SKILL_ALREADY_EXISTS(409, "이미 존재하는 기술스택입니다."),

    //500 Internal Server Error
    INTERNAL_SERVER_ERROR(500,"서버 에러");

    private int status;

    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }


}
