package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Course;
import org.university.repository.mapper.course.MapperConstants;
import org.university.repository.mapper.course.MapperExecutor;
import org.university.repository.mapper.course.StatementValueSetter;
import org.university.sql.course.SqlConstants;
import org.university.sql.course.SqlParameters;
import org.university.web.dto.course.RequestCourseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    private static final Logger log = LoggerFactory.getLogger(CourseRepository.class);

    public Optional<List<Course>> findById(Integer id) throws SQLException {
        String sql = SqlConstants.FIND_BY_ID;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.READ_CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Course> courses = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return courses.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(courses);
    }

    public Optional<List<Course>> findAll() throws SQLException {
        String sql = SqlConstants.FIND_ALL;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ALL;
        MapperExecutor mapperExecutor = MapperConstants.READ_CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forAll();
        List<Course> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    public Optional<List<Course>> create(RequestCourseDto requestDto) throws SQLException {
        String sql = SqlConstants.INSERT;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCreate(requestDto);
        List<Course> professors = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return professors.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(professors);
    }

    public Optional<List<Course>> update(RequestCourseDto requestDto, Integer id) throws SQLException {
        String sql = SqlConstants.UPDATE;
        log.info("Виконується update: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_UPDATE;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forUpdate(id,requestDto);
        List<Course> courses = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return courses.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(courses);
    }

    public Optional<List<Course>> deleteById(Integer id) throws SQLException {
        String sql = SqlConstants.DELETE;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_DELETE_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CREATE_UPDATE_DELETE_CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Course> students = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return students.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(students);
    }

    private static List<Course> executeSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction, MapperExecutor mapperExecutor) throws SQLException {
        List<Course> result;
        try (Connection conn = DbConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            log.info("Created PreparedStatement stmt");
            stmtConfigurationAction.setValues(stmt, sqlParams);
            log.info("Set values of placeholders in stmt");
            try (ResultSet rs = stmt.executeQuery()) {
                result = createCoursesList(rs, mapperExecutor);
            }
        }
        return result;
    }

    private static List<Course> createCoursesList(ResultSet rs, MapperExecutor mapperExecutor) throws SQLException {
        List<Course> students = new ArrayList<>();
        while (rs.next()) {
            Course course = mapperExecutor.execute(rs);
            students.add(course);
        }
        return students.isEmpty() ? new ArrayList<>() : students;
    }
}





