package com.example.softarex.service.field;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.Field;

@Transactional
public interface FieldService {

    Field createField(Field field);

    void deleteField(long id);

    Field updateField(Field field);

    List<Field> getAll(int offset, int limit);

    Field getField(long id);

    List<Field> getAllActive();

    long getFieldCount();
}
