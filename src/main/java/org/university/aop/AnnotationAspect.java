package org.university.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.university.web.dto.validation.RequestDtoValidator;

import java.io.IOException;

@Aspect
public class AnnotationAspect {
    public AnnotationAspect() {
        System.out.println("Aspect constructor");
    }
    @Pointcut("execution(* org.university.web.controller..*.create(*))")
    public void controllerCreateMethods() {}

    @Before(value = "controllerCreateMethods()")
    public void validateDtoBeforeController(JoinPoint joinPoint) throws IOException {
        System.out.println("Aspect works");
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            Object dto = args[0]; // Извлекаем первый аргумент (DTO)
            RequestDtoValidator.validateRequestDto(dto);
        }
    }
}

