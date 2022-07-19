package com.example.softarex.enums.field_types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum FieldTypes {
    SINGLE_LINE_TEXT("text"),
    MULTILINE_TEXT("multiline"),
    RADIO_BUTTON("radio"),
    CHECKBOX("checkbox"),
    COMBOBOX("combobox"),
    DATE("date")
    ;
    @Setter
    private String htmlType;
}
