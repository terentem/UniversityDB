package org.university.web.dto.enrollment;

import org.university.domain.model.Enrollment;

public record ResponseEnrollmentDto(
        int studentId,
        int offeringId,
        String grade
) {

    public static ResponseEnrollmentDto toReadPostPutDeleteDto(Enrollment enrollment) {
        return new ResponseEnrollmentDto(
                enrollment.studentId(),
                enrollment.offeringId(),
                enrollment.grade());
    }

}
