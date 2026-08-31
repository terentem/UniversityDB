package org.university.web.dto.enrollment;

public record RequestEnrollmentDto(
        int studentId,
        int offeringId,
        String grade
) {
}

