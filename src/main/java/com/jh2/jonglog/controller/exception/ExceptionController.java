package com.jh2.jonglog.controller.exception;

import com.jh2.jonglog.exception.JongException;
import com.jh2.jonglog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(JongException.class)
    public ResponseEntity<ErrorResponse> commonRunTimeException(JongException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                                            .code(String.valueOf(statusCode))
                                            .message(e.getMessage())
                                            .validation(e.getValidation())
                                            .build();

        return ResponseEntity.status(HttpStatus.valueOf(statusCode))
                .body(body);
    }
}
