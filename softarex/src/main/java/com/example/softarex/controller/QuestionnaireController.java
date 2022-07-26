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
import com.example.softarex.converter.UserAnswerDtoConverter;
import com.example.softarex.dto.FieldDto;
import com.example.softarex.dto.UserAnswerDto;
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

    @GetMapping(value = "/lastId")
    private long getLastId(){
        return questionnaireService.getLastId();
    }

    @GetMapping
    private List<UserQuestionnaireDto> getAllUserAnswers(){
        return questionnaireService.getAllUserAnswers();
    }

    @GetMapping(value = "/{id}")
    private List<FieldDto> getAllActiveFields(@PathVariable long id){
        questionnaireService.checkExisting(id);
        List<FieldDto> dtoList = new ArrayList<>();
        for (Field field : fieldService.getAllActive()){
            dtoList.add(FieldDtoConverter.convertFieldToDto(field));
        }
        return dtoList;
    }

    @PostMapping(value = "/{id}")
    private void createResponse(@PathVariable long id, @Valid @RequestBody List<UserAnswerDto> userAnswerList){
        questionnaireService.checkExisting(id);
        List<UserAnswer> userAnswers = new ArrayList<>();
        userAnswerList.forEach(userAnswerDto ->{
            if(userAnswerDto.getAnswer() == null  || userAnswerDto.getAnswer().equals("") && userAnswerDto.isRequired()){
                throw new IncorrectQuestionnaireInputException(String.format("%s is required",
                    fieldService.getField(userAnswerDto.getId()).getLabel()));
            }
            userAnswers.add(UserAnswerDtoConverter.convertUserAnswerDtoToUserAnswer(userAnswerDto,
                fieldService.getField(userAnswerDto.getId()), id));
        });
        userAnswers.forEach(questionnaireService::saveUserAnswer);
    }
}
