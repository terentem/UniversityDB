package org.university.web.dto.validation;


public class ViolationDto {
    private String field;
    private String message;

    public void setField(String field) {
        this.field = field;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
