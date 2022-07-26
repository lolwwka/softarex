package com.example.softarex.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAnswerDto {
    @NotBlank
    private long id;
    @NotBlank
    private String answer;
    @NotBlank
    private boolean required;
}
