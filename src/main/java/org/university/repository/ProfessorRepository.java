package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.sql.SqlScript;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorRepository {

    private static final Logger log = LoggerFactory.getLogger(ProfessorRepository.class);
    private final SqlScript script;

    public ProfessorRepository(SqlScript script) {
        this.script = script;
    }

    public Optional<List<Professor>> getProfessorsById(Integer id) throws SQLException {
        String sql = script.getProfessorsById(id);
        log.info("script={}", sql);
        List<Professor> professors = professors(sql);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> getProfessorsByEmail(String email) throws SQLException {
        String sql = script.getProfessorsByEmail(email);
        log.info("script={}", sql);
        List<Professor> professors = professors(sql);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> getProfessorsByName(String name) throws SQLException {
        String sql = script.getProfessorsByName(name);
        log.info("script={}", sql);
        List<Professor> professors = professors(sql);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> getAllProfessors(String params) throws SQLException {
        String sql = script.getAllProfessors(params);
        log.info("script={}", sql);
        List<Professor> professors = professors(sql);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    private static List<Professor> professors(String sql) throws SQLException {
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
                        rs.getInt("department_id"),
                        rs.getString("department")
                );
                professors.add(professor);
            }
        }
        return professors.isEmpty() ? new ArrayList<>() : professors;
    }

    public Optional<List<Professor>> postProfessor(String name, String email, int departmentId) throws SQLException {
        String department = "";
        String sql = script.insertProfessor(name, email, departmentId);
        log.info("Виконується інсерт: sql {} ", sql);
        int generatedId = getProfessorId(sql);
        log.info("new professor id = {}", generatedId);
        if (generatedId > 0) {
            List<Professor> createdProfessors = new ArrayList<>();
            createdProfessors.add(new Professor(generatedId, name, email, departmentId, department));
            log.info("Created new professor {}", createdProfessors.getFirst());
            return Optional.of(createdProfessors);
        } else {
            throw new IllegalArgumentException();
        }

    }

    private static int getProfessorId(String sql) throws SQLException {
        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            // "23505" — это стандартный код PostgreSQL для unique_violation
            if ("23505".equals(e.getSQLState())) {
                log.warn("Попытка добавить дубликат email: {}", e.getMessage());
                throw new IllegalArgumentException("Professor with this email already exists!");
            }

            log.error("Insert ERROR: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<List<Professor>> putProfessor(int id,String name, String email, int departmentId) throws SQLException {
        String department = "";
        String sql = script.updateProfessor(id, name, email, departmentId);
        log.info("Виконується інсерт: sql {} ", sql);
        int generatedId = getProfessorId(sql);
        log.info("new professor id = {}", generatedId);
        if (generatedId > 0) {
            List<Professor> createdProfessors = new ArrayList<>();
            createdProfessors.add(new Professor(generatedId, name, email, departmentId, department));
            log.info("Created new professor {}", createdProfessors.getFirst());
            return Optional.of(createdProfessors);
        } else {
            throw new IllegalArgumentException();
        }

    }
}
