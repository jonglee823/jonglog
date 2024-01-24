package com.jh2.jonglog.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public abstract class JongException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public JongException(){

    }

    public JongException(String message) {
        super(message);
    }

    public JongException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }



}
