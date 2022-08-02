package com.example.softarex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.example.softarex.enums.field_types.FieldTypes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "field")
@Getter
@Setter
@NoArgsConstructor
public class Field extends BaseEntity{

    @NotBlank
    private String label;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FieldTypes type;
    private boolean required;
    private boolean active;
    private String options;
}
