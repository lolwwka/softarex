package com.example.softarex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.constants.routs.QuestionnaireControllerRouts;
import com.example.softarex.converter.FieldDtoConverter;
import com.example.softarex.dto.FieldDto;
import com.example.softarex.dto.UserQuestionnaireDto;
import com.example.softarex.entity.Field;
import com.example.softarex.entity.UserAnswer;
import com.example.softarex.exception.custom.IncorrectQuestionnaireInputException;
import com.example.softarex.service.field.FieldService;
import com.example.softarex.service.questionnaire.QuestionnaireService;


@RestController
@RequestMapping(QuestionnaireControllerRouts.MAIN_ROUT)
public class QuestionnaireController {

    private final FieldService fieldService;
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(FieldService fieldService, QuestionnaireService questionnaireService) {
        this.fieldService = fieldService;
        this.questionnaireService = questionnaireService;
    }

    @GetMapping(value = QuestionnaireControllerRouts.LAST_ID)
    private long getLastId() {
        return questionnaireService.getLastId();
    }

    @GetMapping(value = QuestionnaireControllerRouts.GET_ALL)
    private List<UserAnswer> getAllUserAnswers(@PathVariable int offset, @PathVariable int limit) {
        return questionnaireService.getAllUserAnswers(offset, limit);
    }

    @GetMapping(value = QuestionnaireControllerRouts.TOTAL_NUM)
    private long getQuestionnaireCount() {
        return questionnaireService.getQuestionnaireCount();
    }

    @GetMapping(value = QuestionnaireControllerRouts.PATH_ID)
    private List<FieldDto> getAllActiveFields(@PathVariable long id) {
        questionnaireService.checkExisting(id);
        List<FieldDto> dtoList = new ArrayList<>();
        for (Field field : fieldService.getAllActive()) {
            dtoList.add(FieldDtoConverter.convertFieldToDto(field));
        }
        return dtoList;
    }

    @PostMapping(value = QuestionnaireControllerRouts.PATH_ID)
    private void createResponse(@PathVariable long id, @Valid @RequestBody UserQuestionnaireDto userAnswerList) {
        questionnaireService.checkExisting(id);
        userAnswerList.getUserCashAnswers().forEach(userAnswerDto -> {
            if (userAnswerDto.getAnswer() == null || userAnswerDto.getAnswer().equals("") && userAnswerDto.isRequired()) {
                throw new IncorrectQuestionnaireInputException(String.format("%s is required",
                    fieldService.getField(userAnswerDto.getFieldId()).getLabel()));
            }
        });
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUserCashAnswer(userAnswerList.getUserCashAnswers());
        questionnaireService.saveUserAnswer(userAnswer);
    }
}
