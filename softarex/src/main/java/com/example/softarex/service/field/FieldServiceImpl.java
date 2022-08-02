package com.example.softarex.service.field;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.Field;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.repository.FieldRepository;

@Transactional
@Service
public class FieldServiceImpl implements FieldService {

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
        fieldRepository.findById(id).ifPresentOrElse(fieldRepository::delete, () -> {
            throw new IncorrectIdException("Field with that id don't exists");
        });
    }

    @Override
    public Field updateField(Field field) {
        fieldRepository.findById(field.getId()).ifPresentOrElse(dbField -> {
            dbField.setOptions(field.getOptions());
            dbField.setActive(field.isActive());
            dbField.setLabel(field.getLabel());
            dbField.setType(field.getType());
            dbField.setRequired(field.isRequired());
            fieldRepository.save(dbField);
        }, () -> {
            throw new IncorrectIdException("Field with that id don't exists");
        });
        return fieldRepository.findById(field.getId()).get();
    }

    @Override
    public List<Field> getAll(int offset, int limit) {
        Pageable fieldPage = PageRequest.of(offset, limit);
        List<Field> fieldList = fieldRepository.findAll(fieldPage).getContent();
        fieldList = fieldList.stream().sorted(Comparator.comparingLong(Field::getId)).collect(Collectors.toList());
        return fieldList;
    }

    @Override
    public Field getField(long id) {
        return fieldRepository.findById(id).orElseThrow(() -> new IncorrectIdException("Field with that id don't exists"));
    }

    @Override
    public List<Field> getAllActive() {
        long count = fieldRepository.count();
        Pageable allActiveFields = PageRequest.of(0, (int) count);
        return fieldRepository.findAllByActiveTrue(allActiveFields).getContent();
    }

    @Override
    public long getFieldCount() {
        return fieldRepository.count();
    }
}
