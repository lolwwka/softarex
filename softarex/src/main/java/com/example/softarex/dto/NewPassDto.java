package com.example.softarex.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class NewPassDto {

    @NotBlank(message = "Current pass can't be empty")
    private String currentPass;

    @NotBlank(message = "New pass can't be empty")
    @Size(max = 25, message = "Password must be less 25 symbols")
    @Size(min = 5, message = "Password must be more 5 symbols")
    private String newPass;

}

