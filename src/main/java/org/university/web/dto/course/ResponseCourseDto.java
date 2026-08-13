package org.university.web.dto.course;

import org.university.domain.model.Course;
import org.university.domain.model.Student;

public record ResponseCourseDto(
        Integer id,
        String courseCode,
        String title,
        Integer numberOfCredits,
        Integer departmentId,
        String department
) {

    public static ResponseCourseDto toPostPutDeleteDto(Course course) {
        return new ResponseCourseDto(
                course.id(),
                course.courseCode(),
                course.title(),
                course.numberOfCredits(),
                course.departmentId(),
                null
        );
    }

    public static ResponseCourseDto toGteDto(Course course) {
        return new ResponseCourseDto(
                course.id(),
                course.courseCode(),
                course.title(),
                course.numberOfCredits(),
                course.departmentId(),
                course.department()
        );
    }
}
