package com.example.softarex.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.softarex.constants.routs.WebSocketRouts;
import com.example.softarex.dto.UserAnswerDto;
import com.example.softarex.dto.UserQuestionnaireDto;

@Controller
public class WebSocketController {

    @MessageMapping(WebSocketRouts.FROM_ROUT)
    @SendTo(WebSocketRouts.TO_ROUT)
    public UserQuestionnaireDto getNewQuestionnaire(List<UserAnswerDto> userAnswerList) throws InterruptedException {
        Thread.sleep(1000);
        UserQuestionnaireDto userQuestionnaireDto = new UserQuestionnaireDto();
        List<String> userAnswers = new ArrayList<>();
        userAnswerList.forEach(userAnswer ->{
            userAnswers.add(userAnswer.getAnswer());
        });
        userQuestionnaireDto.setUserAnswers(userAnswers);
        return userQuestionnaireDto;
    }
}
