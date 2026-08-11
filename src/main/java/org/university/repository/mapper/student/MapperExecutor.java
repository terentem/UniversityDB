package org.university.repository.mapper.student;

import org.university.domain.model.Professor;
import org.university.domain.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperExecutor {
    Student execute(ResultSet rs)throws SQLException;
}
