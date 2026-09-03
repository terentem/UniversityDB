package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Student;
import org.university.repository.mapper.student.MapperConstants;
import org.university.repository.mapper.student.MapperExecutor;
import org.university.repository.mapper.student.StatementValueSetter;
import org.university.sql.student.SqlConstants;
import org.university.sql.student.SqlParameters;
import org.university.web.dto.student.RequestStudentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {

    private static final Logger log = LoggerFactory.getLogger(StudentRepository.class);

    public Optional<List<Student>> findById(Integer id) throws SQLException {
        String sql = SqlConstants.FIND_BY_ID;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Student> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    public Optional<List<Student>> findByInterestId(Integer id) throws SQLException {
        String sql = SqlConstants.FIND_STUDENTS_BY_INTERESTS;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Student> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    public Optional<List<Student>> findAll() throws SQLException {
        String sql = SqlConstants.FIND_ALL;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ALL;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forAll();
        List<Student> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    public Optional<List<Student>> create(RequestStudentDto requestStudentDto) throws SQLException {
        String sql = SqlConstants.INSERT;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCreate(requestStudentDto);
        List<Student> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Student>> update(RequestStudentDto requestStudentDto, Integer id) throws SQLException {
        String sql = SqlConstants.UPDATE;
        log.info("Виконується update: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_UPDATE;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forUpdate(id,requestStudentDto);
        List<Student> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    public Optional<List<Student>> deleteById(Integer id) throws SQLException {
        String sql = SqlConstants.DELETE;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_DELETE_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Student> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    private static List<Student> executeSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction, MapperExecutor mapperExecutor) throws SQLException {
        List<Student> result;
        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            log.info("Created PreparedStatement stmt");
            stmtConfigurationAction.setValues(stmt, sqlParams);
            log.info("Set values of placeholders in stmt");
            try (ResultSet rs = stmt.executeQuery()) {
                result = createStudentsList(rs, mapperExecutor);
            }
        }
        return result;
    }

    private static List<Student> createStudentsList(ResultSet rs, MapperExecutor mapperExecutor) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = mapperExecutor.execute(rs);
            students.add(student);
        }
        return students.isEmpty() ? new ArrayList<>() : students;
    }
}





