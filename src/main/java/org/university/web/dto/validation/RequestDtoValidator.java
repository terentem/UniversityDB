package org.university.web.dto.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.university.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestDtoValidator {
    public static <T> Boolean validateRequestDto(T requestDto) throws IOException {
        System.out.println("Start Validation");
        List<ViolationDto> violationsList = new ArrayList<>();
        BadRequestDto badRequestDto = new BadRequestDto();
        try (ValidatorFactory factory = ApplicationContext.FACTORY) {
            Validator validator = factory.getValidator();
            for (ConstraintViolation<T> v : validator.validate(requestDto)) {
                ViolationDto violationDto = new ViolationDto();
                violationDto.setField(v.getPropertyPath().toString());
                violationDto.setMessage(v.getMessage());
                violationsList.add(violationDto);
            }
        }
        if (violationsList.size() > 0) {
            badRequestDto.setViolations(violationsList);
            System.out.println("Not successfully ended validation. violations size=" + badRequestDto.getViolations().size());
            throw new DtoValidationException(badRequestDto);
        }
        System.out.println("Validation ends with success");
        return true;
    }

}
