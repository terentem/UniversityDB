package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.OutputProfessorDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResponseProfessorMapper {
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    public static List<OutputProfessorDto> createGetHttpResponseDto(Optional<List<Professor>> optionalResult) {
        List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
        List<OutputProfessorDto> ResponseDto = professorsList.stream()
                .map(p -> new OutputProfessorDto(p.id(),p.name(), p.email(), p.department(),p.departmentId()))
                .toList();
        return ResponseDto;
    }

    public static List<OutputProfessorDto> createPostPutDeleteHttpResponseDto(Optional<List<Professor>> optionalResult) {
        List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
        List<Professor> professorList = optionalResult.orElse(Collections.emptyList());
        List<OutputProfessorDto> postProfessorDto = professorList.stream()
                .map(p -> new OutputProfessorDto(p.id(), p.name(), p.email(), p.departmentId()))
                .toList();
        return postProfessorDto;
    }
}



