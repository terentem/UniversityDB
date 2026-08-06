package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.repository.mapper.professor.MapperConstants;
import org.university.repository.mapper.professor.MapperExecutor;
import org.university.repository.mapper.professor.StatementValueSetter;
import org.university.sql.professor.SqlConstants;
import org.university.sql.professor.SqlParameters;
import org.university.web.dto.professor.RequestProfessorDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorRepository {

    private static final Logger log = LoggerFactory.getLogger(ProfessorRepository.class);

    public Optional<List<Professor>> findById(Integer id) throws SQLException {
        String sql = SqlConstants.FIND_PROFESSOR_BY_ID;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.READ_CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = new SqlParameters(id, null, null, null);
        List<Professor> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> findAll() throws SQLException {
        String sql = SqlConstants.FIND_ALL_PROFESSORS;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ALL;
        MapperExecutor mapperExecutor = MapperConstants.READ_CONSTANT;
        SqlParameters paramsForStmtConfiguration = new SqlParameters(null, null, null, null);
        List<Professor> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> createProfessor(RequestProfessorDto requestProfessorDto) throws SQLException {
        String sql = SqlConstants.INSERT_PROFESSOR;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        SqlParameters paramsForStmtConfiguration = new SqlParameters(requestProfessorDto.name(), requestProfessorDto.email(), requestProfessorDto.departmentId());
        List<Professor> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> updateProfessor(RequestProfessorDto professorDto, Integer pathVariavle) throws SQLException {

        String sql = SqlConstants.UPDATE_PROFESSOR;
        log.info("Виконується update: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_UPDATE;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        SqlParameters paramsForStmtConfiguration = new SqlParameters(pathVariavle, professorDto.name(), professorDto.email(), professorDto.departmentId());
        List<Professor> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Professor>> deleteById(Integer id) throws SQLException {
        String sql = SqlConstants.DELETE_PROFESSOR;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_DELETE_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = new SqlParameters(id, null, null, null);
        List<Professor> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    private static List<Professor> executeSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction, MapperExecutor mapperExecutor) throws SQLException {
        List<Professor> result;
        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            log.info("Created PreparedStatement stmt");
            stmtConfigurationAction.setValues(stmt, sqlParams);
            log.info("Set values of placeholders in stmt");
            try (ResultSet rs = stmt.executeQuery()) {
                result = createProfessorsList(rs, mapperExecutor);
            }
        }
        return result;
    }

    private static List<Professor> createProfessorsList(ResultSet rs, MapperExecutor mapperExecutor) throws SQLException {
        List<Professor> professors = new ArrayList<>();
        while (rs.next()) {
            Professor professor = mapperExecutor.execute(rs);
            professors.add(professor);
        }
        return professors.isEmpty() ? new ArrayList<>() : professors;
    }
}





