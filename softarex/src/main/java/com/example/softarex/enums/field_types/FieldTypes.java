package com.example.softarex.enums.field_types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum FieldTypes {
    SINGLE_LINE_TEXT("Single line text"),
    MULTILINE_TEXT("Multiline text"),
    RADIO_BUTTON("Radio button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");
    @Setter
    private String htmlType;
}
