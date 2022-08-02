package com.example.softarex.converter;

import java.security.InvalidParameterException;

import com.example.softarex.dto.FieldDto;
import com.example.softarex.entity.Field;
import com.example.softarex.enums.field_types.FieldTypes;

public class FieldDtoConverter {

    public static FieldDto convertFieldToDto(Field field) {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setId(field.getId());
        fieldDto.setLabel(field.getLabel());
        fieldDto.setType(field.getType().getHtmlType());
        fieldDto.setActive(field.isActive());
        fieldDto.setRequired(field.isRequired());
        fieldDto.setOptions(field.getOptions());
        return fieldDto;
    }

    public static Field convertFieldDtoToField(FieldDto fieldDto) {
        Field field = new Field();
        field.setId(fieldDto.getId());
        field.setActive(fieldDto.isActive());
        field.setLabel(fieldDto.getLabel());
        FieldTypes fieldType = convertStringToEnum(fieldDto.getType());
        if (fieldType != null) {
            field.setType(fieldType);
        }
        else {
            throw new InvalidParameterException(String.format("Inputted type : %s incorrect", fieldDto.getType()));
        }
        field.setRequired(fieldDto.isRequired());
        field.setOptions(fieldDto.getOptions());
        return field;
    }


    private static FieldTypes convertStringToEnum(String string) {
        for (FieldTypes fieldType : FieldTypes.values()) {
            if (fieldType.getHtmlType().equals(string)) {
                return fieldType;
            }
        }
        return null;
    }
}
