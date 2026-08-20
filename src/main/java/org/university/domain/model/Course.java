package org.university.domain.model;

public record Course(
        Integer id,
        String courseCode,
        String title,
        Integer numberOfCredits,
        Integer departmentId,
        String department,
        String courseCategory
) {

}
