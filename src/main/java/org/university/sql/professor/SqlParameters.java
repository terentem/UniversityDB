package org.university.sql.professor;

public record SqlParameters(Integer id,
                            String name,
                            String email,
                            Integer departmentId) {

    public SqlParameters(String name,
                         String email,
                         Integer departmentId) {
        this(null, name, email, departmentId);
    }
}
