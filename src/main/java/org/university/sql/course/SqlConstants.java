package org.university.sql.course;

import org.university.repository.mapper.course.StatementValueSetter;

public class SqlConstants {
    private SqlConstants() {
    }

    ;

    public static final String FIND_ALL = """
            SELECT s.*, d.name AS department
            FROM courses s
            JOIN departments d
                 ON s.department_id = d.id
            """;

    public static final String FIND_BY_ID = """
            SELECT s.*, d.name AS department
            FROM courses s
            JOIN departments d
                 ON s.department_id = d.id
            WHERE s.id=?
            """;

    public static final String INSERT = """
            INSERT INTO courses (course_code, title, number_of_credits, department_id, course_category)
            VALUES (?, ?, ?, ?,?)
            RETURNING *
            """;

    public static final String UPDATE = """
            UPDATE courses
            SET course_code=?, title=?,number_of_credits=?,department_id=?,course_category=?
            WHERE id=?
            RETURNING *
            """;

    public static final String DELETE = """
            DELETE FROM courses
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
                stmt.setString(1, params.courseCode());
                stmt.setString(2, params.title());
                stmt.setInt(3, params.numberOfCredits());
                stmt.setInt(4, params.departmentId());
                stmt.setString(5,params.courseCategory());
            };

    public static final StatementValueSetter ACTION_FOR_UPDATE =
            (stmt, params) -> {
                stmt.setString(1, params.courseCode());
                stmt.setString(2, params.title());
                stmt.setInt(3, params.numberOfCredits());
                stmt.setInt(4, params.departmentId());
                stmt.setString(5, params.courseCategory());
                stmt.setInt(6, params.id());
                            };

    public static final StatementValueSetter ACTION_FOR_DELETE_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.id());
}


