package org.example.domain.dto;

public record ProfessorDto (
        Long id,
        String name,
        String email,
        Long departmentId) implements UniversityDto{
}
