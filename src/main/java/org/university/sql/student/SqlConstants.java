package org.university.sql.student;

import org.university.repository.mapper.student.StatementValueSetter;

public class SqlConstants {
    private SqlConstants() {
    }

    ;

    public static final String FIND_ALL = """
            SELECT s.*
            FROM students s
            """;

    public static final String FIND_BY_ID = """
            SELECT *
            FROM students
            WHERE id=?
            """;

    public static final String INSERT = """
            INSERT INTO students (name, email, enrollment_date, birthday, funding_source, admission_score, has_special_needs, address, phone_number, gender,academic_interests)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)
            RETURNING *
            """;

    public static final String UPDATE = """
            UPDATE students
            SET name=?, email=?,enrollment_date=?,birthday=?, funding_source=?,admission_score=?,has_special_needs=?,address=?,phone_number=?,gender=?,academic_interests=?
            WHERE id=?
            RETURNING *
            """;

    public static final String DELETE = """
            DELETE FROM students
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
                stmt.setObject(3, params.enrollmentDate());
                stmt.setObject(4, params.birthday());
                stmt.setString(5, params.fundingSource());
                stmt.setLong(6, params.admissionScore());
                stmt.setBoolean(7, params.specialNeeds());
                stmt.setString(8, params.address());
                stmt.setString(9, params.phoneNumber());
                stmt.setString(10, params.gender());
                stmt.setObject(11, params.academicInterests());
            };

    public static final StatementValueSetter ACTION_FOR_UPDATE =
            (stmt, params) -> {
                stmt.setString(1, params.name());
                stmt.setString(2, params.email());
                stmt.setObject(3, params.enrollmentDate());
                stmt.setObject(4, params.birthday());
                stmt.setString(5, params.fundingSource());
                stmt.setLong(6, params.admissionScore());
                stmt.setBoolean(7, params.specialNeeds());
                stmt.setString(8, params.address());
                stmt.setString(9, params.phoneNumber());
                stmt.setString(10, params.gender());
                stmt.setObject(11,params.academicInterests());
                stmt.setInt(12, params.id());
            };

    public static final StatementValueSetter ACTION_FOR_DELETE_BY_ID =
            (stmt, params) -> stmt.setInt(1, params.id());
}


