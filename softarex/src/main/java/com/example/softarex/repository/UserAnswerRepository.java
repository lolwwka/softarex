package com.example.softarex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.softarex.entity.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByUserQuestionnaireId(long userQuestionnaireId);
}
