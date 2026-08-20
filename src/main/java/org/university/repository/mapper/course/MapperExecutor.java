package org.university.repository.mapper.course;

import org.university.domain.model.Course;
import org.university.domain.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperExecutor {
    Course execute(ResultSet rs)throws SQLException;
}
