package com.example.softarex.exception.custom;

public class IncorrectUserPassException extends RuntimeException{

    public IncorrectUserPassException(String message) {
        super(message);
    }

}
