package org.university.domain.model;

public record Enrollment(
        int studentId,
        int offeringId,
        String grade
) {
}
