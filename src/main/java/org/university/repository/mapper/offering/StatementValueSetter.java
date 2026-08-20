package org.university.repository.mapper.offering;

import org.university.sql.offering.SqlParameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementValueSetter {
    void setValues(PreparedStatement stmt, SqlParameters sqlParams)
            throws SQLException;
}


