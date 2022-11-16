package com.codestates.mainproject.advice;

import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException", e);
    final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult());
    return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getConstraintViolations());

        return errorResponse;
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(e.getExceptionCode());
        log.error("handlerBusinessLogicException", e);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }
}
