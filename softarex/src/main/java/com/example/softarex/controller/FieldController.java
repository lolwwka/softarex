package com.example.softarex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.converter.FieldDtoConverter;
import com.example.softarex.dto.FieldDto;
import com.example.softarex.entity.Field;
import com.example.softarex.service.field.FieldService;

@RestController
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping()
    private List<FieldDto> getAll(){
        List<FieldDto> dtoList = new ArrayList<>();
        for(Field field : fieldService.getAll()){
            dtoList.add(FieldDtoConverter.convertFieldToDto(field));
        }
        return dtoList;
    }

    @PostMapping
    private FieldDto createField(@Valid @RequestBody FieldDto fieldDto){
        return FieldDtoConverter.convertFieldToDto(fieldService.createField(FieldDtoConverter.convertFieldDtoToField(fieldDto)));
    }
}
