package org.example.repository;

import org.example.domain.model.Professor;
import org.example.sql.SqlScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorRepository {

    private static final Logger log = LoggerFactory.getLogger(ProfessorRepository.class);

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

        if (paramsArr[0].equals("all")) {
            sql = script.getAllProfessors(params);
            log.info("script={}", sql);
            log.info("paramsArr[0]={}", paramsArr[0]);
        } else if (paramsArr[0].equals("")) {
            sql = script.getAllProfessors(params);
            log.info("Empty paramsArr[0]={}", paramsArr[0]);
            return Optional.empty();
        } else {
            sql = paramsArr[0].contains("@") ? script.getProfessorsByEmail(paramsArr[0]) : script.getProfessorsByName(paramsArr[0]);
            log.info("script={}", sql);
        }

        List<Professor> professors = new ArrayList<>();
        // Правильный синтаксис try-with-resources для автоматического закрытия stmt и rs
        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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
        System.out.println("Зайшли в ProfessorRepository");
        String[] paramsArr;
        if (params != null) {
            paramsArr = params.split(",");
            System.out.println("paramsArr=" + paramsArr[0] + " " + paramsArr[1] + " " + paramsArr[2]);
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

        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                int generatedId = 0;
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1); // Витягуємо згенерований ID з першої колонки
                    }
                }
                List<Professor> createdProfessors = new ArrayList<>();
                createdProfessors.add(new Professor(generatedId, name, email, departmentId));

                return Optional.of(createdProfessors);
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка інсерту");
            return Optional.empty();
        }

        return Optional.empty();
    }
}