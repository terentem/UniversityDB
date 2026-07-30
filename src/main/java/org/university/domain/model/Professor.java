package org.university.domain.model;

public record Professor(
        int id,
        String name,
        String email,
        int departmentId,
        String department) {

    public Professor(int id, String name, String email, int departmentId) {
        this(id, name, email, departmentId, null);

    }
}
