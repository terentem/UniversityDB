package org.university.sql.enrollment;

import org.university.domain.model.Student;
import org.university.repository.mapper.enrollment.StatementValueSetter;

public class SqlConstants {
    private SqlConstants() {
    }

    ;
    public static final String FIND_BY_OFFERING_ID = """
            SELECT *
            FROM students
            WHERE offering_id=?;
            """;

    public static final String FIND_INTEREST_BY_COURSE_ID = """
            SELECT *
            FROM courses_interests
            WHERE course_id=?;
            """;

    public static final String FIND_STUDENTS_BY_INTERESTS = """
            SELECT *
            FROM students
            WHERE ?= ANY(academic_interests);
            """;

    public static final String INSERT = """
            INSERT INTO offerings_students (student_id,offering_id, grade)
            VALUES (?, ?, ? )
            RETURNING *
            """;

    public static final String UPDATE = """
            UPDATE offerings_students
            SET grade=?
            WHERE student_id=? and offering_id=?
            RETURNING *
            """;

    public static final String DELETE = """
            DELETE FROM offerings_students
            WHERE student_id=? and offering_id=?
            RETURNING *
            """;

    public static final StatementValueSetter ACTION_FOR_FIND_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.interestId());


    public static final StatementValueSetter ACTION_FOR_INSERT =
            (stmt, params) -> {
                stmt.setInt(1, params.studentId());
                stmt.setInt(2, params.offeringId());
                stmt.setString(3, params.grade());
            };

    public static final StatementValueSetter ACTION_FOR_INSERT_BY_BUTCH =
            (stmt, params) -> {
                int offeringId = params.offeringId();
                if (params.students() != null) {
                    for (Student student : params.students()) {
                        stmt.setInt(1, student.id());
                        stmt.setInt(2, offeringId);
                        stmt.setNull(3, java.sql.Types.VARCHAR);
                        stmt.addBatch();
                    }
                }
            };

    public static final StatementValueSetter ACTION_FOR_UPDATE =
            (stmt, params) -> {
                stmt.setString(1, params.grade());
                stmt.setInt(2, params.studentId());
                stmt.setInt(3, params.offeringId());
            };

    public static final StatementValueSetter ACTION_FOR_DELETE_BY_ID =
            (stmt, params) -> {
                stmt.setInt(1, params.studentId());
                stmt.setInt(2, params.offeringId());

            };
}


