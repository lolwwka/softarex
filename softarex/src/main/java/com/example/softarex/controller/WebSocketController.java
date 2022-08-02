package com.example.softarex.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.softarex.constants.routs.WebSocketRouts;
import com.example.softarex.entity.UserAnswer;
import com.example.softarex.service.questionnaire.QuestionnaireService;

@Controller
public class WebSocketController {

    private final QuestionnaireService questionnaireService;

    public WebSocketController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @MessageMapping(WebSocketRouts.FROM_ROUT)
    @SendTo(WebSocketRouts.TO_ROUT)
    public UserAnswer getNewQuestionnaire(@PathVariable long id) throws InterruptedException {
        Thread.sleep(5000);
        return questionnaireService.get(id);
    }
}
