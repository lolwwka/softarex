package com.example.softarex.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private List<String> details;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}
