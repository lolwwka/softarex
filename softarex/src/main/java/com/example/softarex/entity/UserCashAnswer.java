package com.example.softarex.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCashAnswer implements Serializable {

    static final long serialVersionUID = -8485895696327472738L;
    private int fieldId;
    private String answer;
    private boolean required;
}
