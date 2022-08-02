package com.example.softarex.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class FieldDto {

    private long id;
    @NotBlank
    private String label;
    @NotBlank
    private String type;
    private String options;
    private boolean required;
    private boolean active;
}
