package com.example.softarex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softarex.constants.routs.FieldControllerRouts;
import com.example.softarex.converter.FieldDtoConverter;
import com.example.softarex.dto.FieldDto;
import com.example.softarex.entity.Field;
import com.example.softarex.service.field.FieldService;

@RestController
@RequestMapping(FieldControllerRouts.MAIN_ROUT)
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping(value = FieldControllerRouts.GET_ALL)
    private List<FieldDto> getAll(@PathVariable int offset, @PathVariable int limit) {
        List<FieldDto> dtoList = new ArrayList<>();
        for (Field field : fieldService.getAll(offset, limit)) {
            dtoList.add(FieldDtoConverter.convertFieldToDto(field));
        }
        return dtoList;
    }

    @GetMapping(value = FieldControllerRouts.FIELD_COUNT)
    private long getFieldCount() {
        return fieldService.getFieldCount();
    }

    @PostMapping
    private FieldDto createField(@Valid @RequestBody FieldDto fieldDto) {
        return FieldDtoConverter.convertFieldToDto(fieldService.createField(FieldDtoConverter.convertFieldDtoToField(fieldDto)));
    }

    @PutMapping(value = FieldControllerRouts.PATH_ID)
    private FieldDto updateField(@PathVariable long id, @Valid @RequestBody FieldDto fieldDto) {
        fieldDto.setId(id);
        return FieldDtoConverter.convertFieldToDto(fieldService.updateField(FieldDtoConverter.convertFieldDtoToField(fieldDto)));
    }

    @GetMapping(value = FieldControllerRouts.PATH_ID)
    private FieldDto getField(@PathVariable long id) {
        return FieldDtoConverter.convertFieldToDto(fieldService.getField(id));
    }

    @DeleteMapping(value = FieldControllerRouts.PATH_ID)
    private void deleteField(@PathVariable long id) {
        fieldService.deleteField(id);
    }
}
