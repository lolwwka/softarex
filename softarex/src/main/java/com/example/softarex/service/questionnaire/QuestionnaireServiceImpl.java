package com.example.softarex.service.questionnaire;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.softarex.dto.UserQuestionnaireDto;
import com.example.softarex.entity.UserAnswer;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.repository.UserAnswerRepository;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService{

    private final UserAnswerRepository userAnswerRepository;

    public QuestionnaireServiceImpl(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    @Override
    public void saveUserAnswer(UserAnswer userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public void checkExisting(long id) {
        if (userAnswerRepository.findByUserQuestionnaireId(id).size() != 0) {
            throw new IncorrectIdException("Questionnaire with that id already exists");
        }
    }

    @Override
    public List<UserQuestionnaireDto> getAllUserAnswers() {
        List<UserQuestionnaireDto> questionnaireDto = new ArrayList<>();
        List<UserAnswer> userAnswers = userAnswerRepository.findAll();
        userAnswers = userAnswers.stream().sorted(Comparator.comparingLong(UserAnswer::getUserQuestionnaireId)).collect(Collectors.toList());
        List<String> userAnswersString = new ArrayList<>();
        List<String> userFieldLabel = new ArrayList<>();
        for (int i = 0; i < userAnswers.size(); i++) {
            if(i > 0){
                if(userAnswers.get(i).getUserQuestionnaireId() != userAnswers.get(i-1).getUserQuestionnaireId()){
                    UserQuestionnaireDto userQuestionnaireDto = new UserQuestionnaireDto();
                    userQuestionnaireDto.setUserAnswers(userAnswersString);
                    userQuestionnaireDto.setFieldLabels(userFieldLabel);
                    questionnaireDto.add(userQuestionnaireDto);
                    userAnswersString = new ArrayList<>();
                    userFieldLabel = new ArrayList<>();
                }
            }
            userAnswersString.add(userAnswers.get(i).getAnswer());
            userFieldLabel.add(userAnswers.get(i).getField().getLabel());
            if(i+1 == userAnswers.size()){
                UserQuestionnaireDto userQuestionnaireDto = new UserQuestionnaireDto();
                userQuestionnaireDto.setUserAnswers(userAnswersString);
                userQuestionnaireDto.setFieldLabels(userFieldLabel);
                questionnaireDto.add(userQuestionnaireDto);
            }
        }
        return questionnaireDto;
    }

    @Override
    public long getLastId() {
        List<UserAnswer> userAnswers = userAnswerRepository.findAll();
        if(userAnswers.size() == 0){
            return 0;
        }
        return userAnswers.stream().max(Comparator.comparingLong(UserAnswer::getUserQuestionnaireId)).get().getUserQuestionnaireId() + 1;
    }
}
