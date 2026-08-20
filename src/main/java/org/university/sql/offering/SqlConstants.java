package org.university.sql.offering;

import org.university.repository.mapper.offering.StatementValueSetter;

public class SqlConstants {
    private SqlConstants() {
    }

    ;

    public static final String FIND_ALL = """
            SELECT *
            FROM offerings
            """;

    public static final String FIND_BY_ID = """
            SELECT *
            FROM offerings
            WHERE id=?
            """;

    public static final String INSERT = """
            INSERT INTO offerings (course_id, term_id, professor_id)
            VALUES (?, ?, ? )
            RETURNING *
            """;

    public static final String UPDATE = """
            UPDATE offerings
            SET course_id=?, term_id=?, professor_id=?
            WHERE id=?
            RETURNING *
            """;

    public static final String DELETE = """
            DELETE FROM offerings
            WHERE id=?
            RETURNING *
            """;

    public static final StatementValueSetter ACTION_FOR_FIND_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.id());

    public static final StatementValueSetter ACTION_FOR_FIND_BY_ALL =
            (stmt, params) -> {
            };

    public static final StatementValueSetter ACTION_FOR_INSERT =
            (stmt, params) -> {
                stmt.setInt(1, params.courseId());
                stmt.setInt(2, params.termId());
                stmt.setInt(3, params.professorId());
            };

    public static final StatementValueSetter ACTION_FOR_UPDATE =
            (stmt, params) -> {
                stmt.setInt(1, params.courseId());
                stmt.setInt(2, params.termId());
                stmt.setInt(3, params.professorId());
                stmt.setInt(4, params.id());
            };

    public static final StatementValueSetter ACTION_FOR_DELETE_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.id());
}


