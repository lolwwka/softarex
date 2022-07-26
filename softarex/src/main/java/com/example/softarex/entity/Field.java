package com.example.softarex.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "field")
@Getter
@Setter
@NoArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String label;
    @NotBlank
    private String type;
    private boolean required;
    private boolean active;
    private String options;
    @OneToMany(
        mappedBy = "field",
        orphanRemoval = true
    )
    private List<UserAnswer> userAnswers;
}
