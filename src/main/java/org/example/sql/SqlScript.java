package org.example.sql;

public class SqlScript {
    String sql = "";

    public String getAllProfessors(String parameter) {
        sql = "SELECT * FROM professors";
        return sql;
    }

    public String getProfessorsByName(String name) {
        sql = "SELECT * FROM professors p where p.name LIKE '%" + name + "%'";
        return sql;
    }

    public String getProfessorsByEmail(String email) {
        sql = "SELECT * FROM professors p where p.email ='" + email + "'";
        return sql;
    }

    public String insertProfessor(String name, String email, int departmentId) {
        sql = "INSERT INTO professors (name, email, department_id) VALUES ('"
                + name + "', '" + email + "', " + departmentId + ")";
        return sql;
    }
}
