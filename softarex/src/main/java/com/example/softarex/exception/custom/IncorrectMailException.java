package com.example.softarex.exception.custom;

public class IncorrectMailException extends RuntimeException {

    public IncorrectMailException(String message) {
        super(message);
    }
}
