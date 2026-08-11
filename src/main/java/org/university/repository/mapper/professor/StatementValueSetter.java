package org.university.repository.mapper.professor;

import org.university.sql.professor.SqlParameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementValueSetter {
    void setValues(PreparedStatement stmt, SqlParameters sqlParams)
            throws SQLException;
}


