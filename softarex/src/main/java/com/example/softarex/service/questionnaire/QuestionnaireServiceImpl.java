package com.example.softarex.service.questionnaire;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.softarex.entity.Field;
import com.example.softarex.entity.UserAnswer;
import com.example.softarex.entity.UserCashAnswer;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.repository.QuestionnaireRepository;
import com.example.softarex.statics.FieldServiceStatic;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final FieldServiceStatic fieldServiceStatic;

    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository, FieldServiceStatic fieldServiceStatic) {
        this.questionnaireRepository = questionnaireRepository;
        this.fieldServiceStatic = fieldServiceStatic;
    }

    @Override
    public void saveUserAnswer(UserAnswer userAnswer) {
        questionnaireRepository.save(userAnswer);
    }

    @Override
    public void checkExisting(long id) {
        if (questionnaireRepository.findById(id).isPresent()) {
            throw new IncorrectIdException("Questionnaire with that id already exists");
        }
    }

    @Override
    public List<UserAnswer> getAllUserAnswers(int offset, int limit) {
        Pageable questionnaireList = PageRequest.of(offset, limit);
        List<UserAnswer> userAnswers = questionnaireRepository.findAll(questionnaireList).getContent();
        userAnswers.forEach(this::deleteDeletedFields);
        List<Field> fieldList = fieldServiceStatic.getAllFields();
        Set<Long> fieldIdSet = new HashSet<>();
        fieldList.forEach(field -> fieldIdSet.add(field.getId()));
        userAnswers.forEach(userAnswer -> {
            Set<Long> fieldIdCopy = new HashSet<>(fieldIdSet);
            fillEmptyAnswers(userAnswer, fieldIdCopy);
        });
        return userAnswers;
    }

    @Override
    public long getLastId() {
        UserAnswer userAnswer = questionnaireRepository.findTopByOrderByIdDesc();
        if (userAnswer == null) {
            return 0;
        }
        return userAnswer.getId() + 1;
    }

    @Override
    public long getQuestionnaireCount() {
        return questionnaireRepository.count();
    }

    private void deleteDeletedFields(UserAnswer userAnswer) {
        List<Field> fieldList = fieldServiceStatic.getAllFields();
        Set<Long> fieldListIds = new HashSet<>();
        Set<Long> userHashAnswersFieldsId = new HashSet<>();
        fieldList.forEach(field -> fieldListIds.add(field.getId()));
        userAnswer.getUserCashAnswer().forEach(userCashAnswer -> userHashAnswersFieldsId.add((long) userCashAnswer.getFieldId()));
        userHashAnswersFieldsId.removeAll(fieldListIds);
        if (userHashAnswersFieldsId.size() != 0) {
            userAnswer.getUserCashAnswer().removeIf(userCashAnswer -> userHashAnswersFieldsId.contains((long) userCashAnswer.getFieldId()));
            UserAnswer userAnswerFromDb = questionnaireRepository.findById(userAnswer.getId()).get();
            userAnswerFromDb.setUserCashAnswer(userAnswer.getUserCashAnswer());
            questionnaireRepository.save(userAnswerFromDb);
        }
    }

    @Override
    public UserAnswer get(long id) {
        UserAnswer userAnswer = questionnaireRepository.findById(id).get();
        deleteDeletedFields(userAnswer);
        List<Field> fieldList = fieldServiceStatic.getAllFields();
        Set<Long> fieldIdSet = new HashSet<>();
        fieldList.forEach(field -> fieldIdSet.add(field.getId()));
        fillEmptyAnswers(userAnswer, fieldIdSet);
        return userAnswer;
    }

    private void fillEmptyAnswers(UserAnswer userAnswer, Set<Long> fieldIdSet) {
        Set<Long> userAnswerIdList = new HashSet<>();
        userAnswer.getUserCashAnswer().forEach(userCashAnswer -> {
            userAnswerIdList.add((long) userCashAnswer.getFieldId());
            if (userCashAnswer.getAnswer().equals("")) {
                userCashAnswer.setAnswer("N/A");
            }
        });
        fieldIdSet.removeAll(userAnswerIdList);
        fieldIdSet.forEach(fieldCopy -> userAnswer.getUserCashAnswer().add(new UserCashAnswer(fieldCopy.intValue(), "N/A", true)));
        userAnswer.setUserCashAnswer(userAnswer.getUserCashAnswer().stream().sorted(Comparator.comparingInt(UserCashAnswer::getFieldId)).collect(Collectors.toList()));
    }
}
