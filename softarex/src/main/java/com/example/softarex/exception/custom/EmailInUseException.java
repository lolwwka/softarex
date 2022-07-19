package com.example.softarex.exception.custom;

public class EmailInUseException extends RuntimeException {

    public EmailInUseException(String message) {
        super(message);
    }
}
