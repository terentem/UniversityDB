package org.university.domain.model;

public record Offering(
        int id,
        int courseId,
        int termId,
        int professorId
) {
}
