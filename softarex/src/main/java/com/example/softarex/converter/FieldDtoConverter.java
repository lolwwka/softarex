package com.example.softarex.converter;

import com.example.softarex.dto.FieldDto;
import com.example.softarex.entity.Field;

public class FieldDtoConverter {
    public static FieldDto convertFieldToDto(Field field){
        FieldDto fieldDto = new FieldDto();
        fieldDto.setId(field.getId());
        fieldDto.setLabel(field.getLabel());
        fieldDto.setType(field.getType());
        fieldDto.setActive(field.isActive());
        fieldDto.setRequired(field.isRequired());
        fieldDto.setOptions(field.getOptions());
        return fieldDto;
    }

    public static Field convertFieldDtoToField(FieldDto fieldDto){
        Field field = new Field();
        field.setId(fieldDto.getId());
        field.setActive(fieldDto.isActive());
        field.setLabel(fieldDto.getLabel());
        field.setType(fieldDto.getType());
        field.setRequired(fieldDto.isRequired());
        field.setOptions(fieldDto.getOptions());
        return field;
    }

}
