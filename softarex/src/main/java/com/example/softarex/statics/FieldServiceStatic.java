package com.example.softarex.statics;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.softarex.entity.Field;
import com.example.softarex.service.field.FieldService;

@Component
public class FieldServiceStatic {

    private final FieldService fieldService;

    public FieldServiceStatic(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    public List<Field> getAllFields() {
        return fieldService.getAll(0, (int) fieldService.getFieldCount());
    }
}
