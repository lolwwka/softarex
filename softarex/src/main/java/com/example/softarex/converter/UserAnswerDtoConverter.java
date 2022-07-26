package com.example.softarex.converter;

import com.example.softarex.dto.UserAnswerDto;
import com.example.softarex.entity.Field;
import com.example.softarex.entity.UserAnswer;

public class UserAnswerDtoConverter {

    public static UserAnswer convertUserAnswerDtoToUserAnswer(UserAnswerDto userAnswerDto, Field field
        , long userQuestionnaireId){
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAnswer(userAnswerDto.getAnswer());
        userAnswer.setUserQuestionnaireId(userQuestionnaireId);
        userAnswer.setField(field);
        return userAnswer;
    }
}
