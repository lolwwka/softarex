package com.example.softarex.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuestionnaireDto {
    private List<String> fieldLabels;
    private List<String> userAnswers;
}
