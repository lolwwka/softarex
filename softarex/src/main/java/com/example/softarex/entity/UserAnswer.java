package com.example.softarex.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Table(name = "user_answer")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String answer;
    @ManyToOne(fetch = FetchType.EAGER)
    private Field field;
    private long userQuestionnaireId;
}
