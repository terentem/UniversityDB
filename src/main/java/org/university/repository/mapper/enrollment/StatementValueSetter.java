package org.university.repository.mapper.enrollment;

import org.university.sql.enrollment.SqlParameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementValueSetter {
    void setValues(PreparedStatement stmt, SqlParameters sqlParams)
            throws SQLException;
}


