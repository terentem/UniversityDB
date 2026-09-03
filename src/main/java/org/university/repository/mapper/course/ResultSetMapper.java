package org.university.repository.mapper.course;


import org.university.domain.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    private ResultSetMapper() {
    }

    public static Course mapToCreateUpdateDelete(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("course_code"),
                rs.getString("title"),
                rs.getInt("number_of_credits"),
                rs.getInt("department_id"),
                null,
                rs.getString("course_category")
        );
    }

    public static Course mapToRead(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("course_code"),
                rs.getString("title"),
                rs.getInt("number_of_credits"),
                rs.getInt("department_id"),
                rs.getString("department"),
                rs.getString("course_category")
        );
    }
}
