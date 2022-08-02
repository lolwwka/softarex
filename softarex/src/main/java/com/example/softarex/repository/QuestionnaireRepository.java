package com.example.softarex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.UserAnswer;
import com.example.softarex.entity.UserCashAnswer;

public interface QuestionnaireRepository extends PagingAndSortingRepository<UserAnswer, Long> {

    UserAnswer findTopByOrderByIdDesc();

    @Transactional
    @Modifying
    @Query("update UserAnswer us set us.userCashAnswer = ?1 where us.id = ?2")
    void updateQuestionnaireInfoById(List<UserCashAnswer> userCashAnswer, long id);
}
