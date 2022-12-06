package com.codestates.mainproject.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    //303 Authentication
    MEMBER_REDIRECTION_LOGIN_SUCCESS(303,"로그인 성공"),
    MEMBER_REDIRECTION_FIND_PASSWORD(303,"패스워드 찾기"),

    //400 Bad Request
    BAD_PARAMETER_ERROR(400, "동일한 비밀번호를 입력하세요."),
    INVALID_INPUT_VALUE(400, "유효한 입력값이 아닙니다."),

    //401 Unauthorized
    TOKEN_IS_INVALID(401, "토큰이 유효하지 않습니다."),
    CODE_IS_INVALID(401, "인증코드가 유효하지 않습니다."),

    //403 Forbidden
    HANDLE_ACCESS_DENIED(403, "접근 권한이 없습니다."),

    EMAIL_VALIDATION_NEED(403, "이메일 인증이 필요합니다."),

    //404 Not Found
    MEMBER_NOT_FOUND(404, "회원이 존재하지 않습니다."),
    ARTICLE_NOT_FOUND(404, "게시글이 존재하지 않습니다."),
    ANSWER_NOT_FOUND(404, "답변이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(404, "댓글이 존재하지 않습니다."),
    HASHTAG_NOT_FOUND(404, "태그가 존재하지 않습니다."),
    INTEREST_NOT_FOUND(404, "관심분야가 존재하지 않습니다."),
    SKILL_NOT_FOUND(404, "기술스택이 존재하지 않습니다."),

    //405 METHOD NOT ALLOWED
    METHOD_NOT_ALLOWED(405, "지원하지 않는 메서드방식입니다."),

    //409 Conflict
    MEMBER_ALREADY_EXISTS(409, "이미 존재하는 회원입니다"),
    MEMBER_NAME_ALREADY_EXISTS(409, "이미 존재하는 회원 이름입니다"),
    MEMBER_EMAIL_ALREADY_EXISTS(409, "이미 존재하는 회원 이메일입니다."),
    MEMBER_GITHUB_ALREADY_EXISTS(409, "이미 존재하는 회원 깃허브입니다."),
    HASHTAG_ALREADY_EXISTS(409, "이미 존재하는 태그입니다."),
    INTEREST_ALREADY_EXISTS(409, "이미 존재하는 관심분야입니다."),
    SKILL_ALREADY_EXISTS(409, "이미 존재하는 기술스택입니다."),

    //500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, "서버 에러");

    private int status;

    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}


