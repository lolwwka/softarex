package com.example.softarex.service.field;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.Field;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.repository.FieldRepository;

@Transactional
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
    public void deleteField(long id) {
        fieldRepository.findById(id).ifPresentOrElse( field -> {
            fieldRepository.delete(field);
            fieldRepository.flush();
        }, () ->{
            throw new IncorrectIdException("Field with that id don't exists");
        });
    }

    @Override
    public Field updateField(Field field) {
        fieldRepository.findById(field.getId()).ifPresentOrElse( field1 -> fieldRepository.updateFieldInfoById(field.isActive(),
            field.getLabel(), field.isRequired(), field.getType(),field.getOptions(), field.getId()),
            () -> {
            throw new IncorrectIdException("Field with that id don't exists");
        });
        return fieldRepository.findById(field.getId()).get();
    }

    @Override
    public List<Field> getAll() {
        return fieldRepository.findAll();
    }

    @Override
    public Field getField(long id) {
        return fieldRepository.findById(id).orElseThrow(() -> new IncorrectIdException("Field with that id don't exists"));
    }

    @Override
    public List<Field> getAllActive() {
        return fieldRepository.findAllByActiveTrue();
    }
}
