package org.university.repository.mapper.enrollment;


import org.university.domain.model.Enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    private ResultSetMapper() {
    }

    public static Enrollment mapToReadCreateUpdateDelete(ResultSet rs) throws SQLException {

        return new Enrollment(
                rs.getInt("student_id"),
                rs.getInt("offering_id"),
                rs.getString("grade")
        );
    }

    public static Integer mapToReadInterestId(ResultSet rs) throws SQLException {
        return rs.getInt("interest_id");
    }
}
