package org.university.repository.mapper.professor;

import org.university.domain.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperExecutor {
    Professor execute( ResultSet rs)throws SQLException;
}
