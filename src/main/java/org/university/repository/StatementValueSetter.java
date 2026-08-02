package org.university.repository;

import org.university.sql.SqlParameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementValueSetter {
    void setValues(PreparedStatement stmt, SqlParameters sqlParams)
            throws SQLException;
}


