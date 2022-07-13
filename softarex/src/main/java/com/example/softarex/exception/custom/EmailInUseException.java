package com.example.softarex.exception.custom;

public class EmailInUseException extends Exception {

    public EmailInUseException(String message) {
        super(message);
    }
}
