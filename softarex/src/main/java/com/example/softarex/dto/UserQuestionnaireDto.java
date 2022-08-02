package com.example.softarex.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.example.softarex.entity.UserCashAnswer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class UserQuestionnaireDto {

    private long id;
    private List<UserCashAnswer> userCashAnswers;
}
