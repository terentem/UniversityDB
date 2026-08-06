package org.university.repository.mapper.professor;

import org.university.domain.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    private ResultSetMapper() {
    }

    public static Professor mapToReadProfessor(ResultSet rs) throws SQLException {

        return new Professor(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("department_id"),
                rs.getString("department")
        );
    }

    public static Professor mapToCreateUpdateDeleteProfessor(ResultSet rs)
            throws SQLException {

        return new Professor(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("department_id")
        );
    }
}
