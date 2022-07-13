package com.example.softarex.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

@Validated
public class NewPassDto {

    @NotBlank(message = "Current pass can't be empty")
    private String currentPass;

    @NotBlank(message = "New pass can't be empty")
    @Size(max = 25, message = "Password must be less 25 symbols")
    @Size(min = 5, message = "Password must be more 5 symbols")
    private String newPass;

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}

