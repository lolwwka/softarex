package com.example.softarex.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_answer")
public class UserAnswer extends BaseEntity {

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<UserCashAnswer> userCashAnswer;

}
