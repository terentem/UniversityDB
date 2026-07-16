package org.example.web;


public enum RequestParameter {
    NAME("name"),
    EMAIL("email"),
    DEPARTMENT("department_id"),
    ID("id"),
    ALL("all");

    private final String value;

    RequestParameter(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

