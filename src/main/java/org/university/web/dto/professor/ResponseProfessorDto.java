package org.university.web.dto.professor;

import org.university.domain.model.Professor;

public record ResponseProfessorDto(//parameters for response for Get request
                                   int id,
                                   String name,
                                   String email,
                                   String department,
                                   Integer departmentId) {

    public static ResponseProfessorDto toGetDto(Professor professor) {
        return new ResponseProfessorDto(
                professor.id(),
                professor.name(),
                professor.email(),
                professor.department(),
                null
        );
    }

    public static ResponseProfessorDto toPostPutDeleteDto(Professor professor) {
        return new ResponseProfessorDto(
                professor.id(),
                professor.name(),
                professor.email(),
                null,
                professor.departmentId()
        );
    }
}
