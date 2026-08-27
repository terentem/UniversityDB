package org.university.repository.mapper.enrollment;

import org.university.domain.model.Enrollment;
import org.university.domain.model.Offering;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface MapperExecutor {
    Enrollment execute(ResultSet rs)throws SQLException;
}
