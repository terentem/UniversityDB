package org.university.sql.professor;

import org.university.repository.mapper.professor.StatementValueSetter;

public class SqlConstants {
    private SqlConstants() {
    }

    ;

    public static final String FIND_ALL_PROFESSORS = """
            SELECT p.*,
                   d.name AS department
            FROM professors p
            JOIN departments d
                 ON p.department_id = d.id
            """;

    public static final String FIND_PROFESSOR_BY_ID = """
            SELECT p.*,
                   d.name AS department
            FROM professors p
            JOIN departments d
                 ON p.department_id = d.id
            WHERE p.id = ?
            """;

    public static final String INSERT_PROFESSOR = """
            INSERT INTO professors (name, email, department_id)
            VALUES (?, ?, ?)
            RETURNING *
            """;


    public static final String UPDATE_PROFESSOR = """
            UPDATE professors
            SET name=?, email=?,department_id=?
            WHERE id=?
            RETURNING *
            """;

    public static final String DELETE_PROFESSOR = """
            DELETE FROM professors
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
                stmt.setString(1, params.name());
                stmt.setString(2, params.email());
                stmt.setInt(3, params.departmentId());
            };

    public static final StatementValueSetter ACTION_FOR_UPDATE =
            (stmt, params) -> {
                stmt.setString(1, params.name());
                stmt.setString(2, params.email());
                stmt.setInt(3, params.departmentId());
                stmt.setInt(4, params.id());
            };

    public static final StatementValueSetter ACTION_FOR_DELETE_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.id());
}


