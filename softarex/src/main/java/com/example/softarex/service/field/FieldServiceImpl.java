package com.example.softarex.service.field;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.softarex.entity.Field;
import com.example.softarex.repository.FieldRepository;

@Service
public class FieldServiceImpl implements FieldService{
    private final FieldRepository fieldRepository;

    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Field createField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public boolean deleteField() {
        return false;
    }

    @Override
    public Field updateField() {
        return null;
    }

    @Override
    public List<Field> getAll() {
        return fieldRepository.findAll();
    }

    @Override
    public Field getField(long id) {
        return null;
    }
}
