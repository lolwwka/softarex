package com.example.softarex.service.questionnaire;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.dto.UserQuestionnaireDto;
import com.example.softarex.entity.UserAnswer;

@Transactional
public interface QuestionnaireService {
    long getLastId();
    void checkExisting(long id);
    void saveUserAnswer(UserAnswer userAnswer);
    List<UserQuestionnaireDto> getAllUserAnswers();
}
