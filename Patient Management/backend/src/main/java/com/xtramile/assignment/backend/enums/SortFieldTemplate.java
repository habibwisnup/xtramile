package com.xtramile.assignment.backend.enums;

public enum SortFieldTemplate {
    ID("pid"),
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    GENDER("gender");

    private String value;

    SortFieldTemplate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
