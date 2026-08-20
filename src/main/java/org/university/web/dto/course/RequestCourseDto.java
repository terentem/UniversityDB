package org.university.web.dto.course;

public record RequestCourseDto(
        Integer id,
        String courseCode,
        String title,
        Integer numberOfCredits,
        Integer departmentId,
        String courseCategory
        ) {
}

