package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.GetProfessorDto;
import org.university.web.dto.PostProfessorDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResponseDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    public static List<GetProfessorDto> createGetHttpResponseDto(Optional<List<Professor>> optionalResult) {
        List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
        List<GetProfessorDto> ResponseDto = professorsList.stream()
                .map(p -> new GetProfessorDto(p.name(), p.email(), p.department()))
                .toList();
        return ResponseDto;
    }

    public static List<PostProfessorDto> createPostHttpResponseDto(Optional<List<Professor>> optionalResult) {
        List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
        List<Professor> professorList = optionalResult.orElse(Collections.emptyList());
        List<PostProfessorDto> postProfessorDto = professorList.stream()
                .map(p -> new PostProfessorDto(p.id(), p.name(), p.email(), p.departmentId()))
                .toList();
        return postProfessorDto;
    }
}



