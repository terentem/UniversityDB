package org.university.repository.mapper.student;


import org.university.domain.model.Student;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ResultSetMapper {

    private ResultSetMapper() {
    }

    public static Student mapToReadCreateUpdateDelete(ResultSet rs) throws SQLException {
        Array sqlArray = rs.getArray("academic_interests");

        int[] academicInterests = null;

        if (sqlArray != null) {
            Integer[] values = (Integer[]) sqlArray.getArray();

            academicInterests = new int[values.length];

            for (int i = 0; i < values.length; i++) {
                academicInterests[i] = values[i];
            }
        }
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getObject("enrollment_date", LocalDate.class),
                rs.getObject("birthday", LocalDate.class),
                rs.getString("funding_source"),
                rs.getLong("admission_score"),
                rs.getBoolean("has_special_needs"),
                rs.getString("address"),
                rs.getString("phone_number"),
                rs.getString("gender"),
                academicInterests
        );
    }
}
