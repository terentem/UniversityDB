package org.university.repository.mapper.offering;

import org.university.domain.model.Offering;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperExecutor {
    Offering execute(ResultSet rs)throws SQLException;
}
