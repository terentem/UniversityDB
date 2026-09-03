package org.university.repository.mapper.offering;


import org.university.domain.model.Offering;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    private ResultSetMapper() {
    }

    public static Offering mapToReadCreateUpdateDelete(ResultSet rs) throws SQLException {


        return new Offering(
                rs.getInt("id"),
                rs.getInt("course_id"),
                rs.getInt("term_id"),
                rs.getInt("professor_id")
        );
    }
}
