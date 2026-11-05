package org.university.web.dto.validation;

import java.util.List;

public class DtoValidationException extends RuntimeException {
    private final BadRequestDto badRequestDto;

    public DtoValidationException(BadRequestDto badRequestDto) {
        // Передаємо повідомлення для стандартного логування винятків
        super("DTO Validation failed with " + badRequestDto.getViolations().size() + " violations");
        this.badRequestDto = badRequestDto;
    }

    public BadRequestDto getBadRequestDto() {
        return badRequestDto;
    }
}
