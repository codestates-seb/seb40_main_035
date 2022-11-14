package com.codestates.mainproject.exception;

import lombok.Getter;

public enum ExceptionCode {

    /* 400 : */
    INVALID_INPUT_VALUE(400,  "INVALID INPUT VALUE"),
    INVALID_INPUT_ATK(400, "INVALID INPUT ACCESS_TOKEN"),

    /* 403 : */
    HANDLE_ACCESS_DENIED(403,"HANDLE ACCESS DENIED"),

    /* 404 : */
    MEMBER_NOT_FOUND(404, "MEMBER NOT FOUND"),
    QUESTION_NOT_FOUND(404, "QUESTION NOT FOUND"),
    ANSWER_NOT_FOUND(404, "ANSWER NOT FOUND"),
    COMMENT_NOT_FOUND(404, "COMMENT NOT FOUND"),

    /* 405 : */
    METHOD_NOT_ALLOWED(405, "METHOD NOT ALLOWED"),

    /* 409 : */
    // MEMBER
    MEMBER_EXISTS(409, "MEMBER_EXISTS"),
    MEMBER_DISPLAY_NAME_EXISTS(409, "MEMBER_DISPLAY_NAME_EXISTS"),
    MEMBER_EMAIL_EXISTS(409, "MEMBER_EMAIL_EXISTS"),
    MEMBER_REDIRECTION_LOGIN_SUCCESS(303,"LOGIN_SUCCESS"),
    MEMBER_REDIRECTION_FIND_PASSWORD(303,"FIND_PASSWORD"),

    /* 500 : */
    INTERNAL_SERVER_ERROR(500,"INTERNAL SERVER ERROR");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
