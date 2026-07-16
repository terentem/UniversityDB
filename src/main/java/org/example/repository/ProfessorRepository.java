package org.example.repository;

import org.example.domain.model.Professor;
import org.example.sql.SqlScript;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorRepository {

    public Optional<List<Professor>> getProfessorsList(String params) throws SQLException {
        //Парсимо params
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
        } else {
            paramsArr = new String[0];
        }

        //Шукаємо необхідний скрипт, в залежності від вхідних параметрів для /get
        SqlScript script = new SqlScript();
        String sql;
        System.out.println("paramsArr length =" + paramsArr.length);

        if (paramsArr.length == 0) {
            sql = script.getAllProfessors(params);
            System.out.println("script=" + sql);
        } else {
            sql = paramsArr[0].contains("@") ? script.getProfessorsByEmail(paramsArr[0]) : script.getProfessorsByName(paramsArr[0]);
        }

        List<Professor> professors = new ArrayList<>();
        // Правильный синтаксис try-with-resources для автоматического закрытия stmt и rs
        try (Connection conn = DbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("department_id")
                );
                professors.add(professor);
            }

        }
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> postProfessor(String params) throws SQLException {
        //Парсимо params
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
            if (paramsArr.length != 3) {
                System.out.println("Помилка валідації: Очікувалося 3 параметри, отримано " + paramsArr.length);
                return Optional.empty();
            }
        } else {
            paramsArr = new String[0];
            System.out.println("Помилка валідації: Очікувалося 3 параметри, отримано " + paramsArr.length);
            return Optional.empty();
        }
        String name = paramsArr[0];
        String email = paramsArr[1];
        int departmentId = Integer.parseInt(paramsArr[2]);

        SqlScript script = new SqlScript();
        String sql = script.insertProfessor(name, email, departmentId);
        System.out.println("Виконується інсерт: " + sql);

        try (Connection conn = DbConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                List<Professor> createdProfessors = new ArrayList<>();
                createdProfessors.add(new Professor(0, name, email, departmentId));

                return Optional.of(createdProfessors);
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка інсерту");
            return Optional.empty();
        }

        return Optional.empty();
    }
}