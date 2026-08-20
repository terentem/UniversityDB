package org.university.repository.mapper.course;

import org.university.sql.course.SqlParameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementValueSetter {
    void setValues(PreparedStatement stmt, SqlParameters sqlParams)
            throws SQLException;
}


