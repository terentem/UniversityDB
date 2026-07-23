package org.university.sql;

public class SqlScript {
    String sql = "";

    public String getAllProfessors(String parameter) {
        sql = "SELECT p.*, d.name as department FROM professors p JOIN departments d on p.department_id=d.id";
        return sql;
    }

    public String getProfessorsByName(String name) {
        sql = "SELECT p.*, d.name as department FROM professors p JOIN departments d on p.department_id=d.id where p.name LIKE '%" + name + "%'";
        return sql;
    }

    public String getProfessorsByEmail(String email) {
        sql = "SELECT p.*, d.name as department FROM professors p JOIN departments d on p.department_id=d.id where p.email ='" + email + "'";
        return sql;
    }

    public String getProfessorsById(Integer id) {
        sql = "SELECT p.*, d.name as department FROM professors p JOIN departments d on p.department_id=d.id where p.id =" + id;
        return sql;
    }

    public String insertProfessor(String name, String email, int departmentId) {
        sql = "INSERT INTO professors (name, email, department_id) VALUES ('"
                + name + "', '" + email + "', " + departmentId + ")";
        return sql;
    }

    public String updateProfessor(int id, String name, String email, int departmentId) {
        sql = "UPDATE professors SET name = '" + name + "', email = '" + email + "', department_id = " + departmentId + " WHERE id = " + id;
        return sql;
    }
}

