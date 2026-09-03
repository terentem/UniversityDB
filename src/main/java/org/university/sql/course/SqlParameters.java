package org.university.sql.course;

import org.university.web.dto.course.RequestCourseDto;

public record SqlParameters(Integer id,
                            String courseCode,
                            String title,
                            Integer numberOfCredits,
                            Integer departmentId,
                            String courseCategory) {

    public static SqlParameters forId(Integer id) {
        return new SqlParameters(
                id,
                null,
                null,
                null,
                null,
                null

        );
    }

    public static SqlParameters forAll() {
        return new SqlParameters(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static SqlParameters forCreate(RequestCourseDto dto) {
        return new SqlParameters(
                null,
                dto.courseCode(),
                dto.title(),
                dto.numberOfCredits(),
                dto.departmentId(),
                dto.courseCategory()
        );
    }

    public static SqlParameters forUpdate(
            Integer id,
            RequestCourseDto dto
    ) {
        return new SqlParameters(
                id,
                dto.courseCode(),
                dto.title(),
                dto.numberOfCredits(),
                dto.departmentId(),
                dto.courseCategory()
        );
    }
}
