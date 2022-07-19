package com.example.softarex.service.field;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.Field;

@Transactional
public interface FieldService {
    Field createField(Field field);
    boolean deleteField();
    Field updateField();
    List<Field> getAll();
    Field getField(long id);
}
