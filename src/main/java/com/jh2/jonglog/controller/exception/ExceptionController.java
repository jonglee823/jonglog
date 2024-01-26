package com.jh2.jonglog.controller.exception;

import com.jh2.jonglog.exception.JongException;
import com.jh2.jonglog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgNotValidException(MethodArgumentNotValidException e){
        ErrorResponse body = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청 입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            body.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.valueOf(400))
                .body(body);
    }
}
