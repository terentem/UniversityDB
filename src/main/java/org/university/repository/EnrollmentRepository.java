package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Enrollment;


import org.university.domain.model.OfferingStudent;
import org.university.repository.mapper.enrollment.MapperConstants;
import org.university.repository.mapper.enrollment.MapperExecutor;
import org.university.repository.mapper.enrollment.StatementValueSetter;
import org.university.sql.enrollment.SqlConstants;
import org.university.sql.enrollment.SqlParameters;
import org.university.web.dto.enrollment.RequestEnrollmentDto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentRepository {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentRepository.class);


    public Optional<List<Enrollment>> create(RequestEnrollmentDto requestDto) throws SQLException {
        String sql = SqlConstants.INSERT;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCreate(requestDto);
        List<Enrollment> enrollments = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return enrollments.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(enrollments);
    }

    public Optional<List<Enrollment>> createbyBatch(OfferingStudent offeringStudent) throws SQLException {
        String sql = SqlConstants.INSERT;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT_BY_BUTCH;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCreateByButch(offeringStudent);
        int totalRowsAffected =executeBatchSql(sql, paramsForStmtConfiguration, stmtConfigurationAction);
        return Optional.of(new ArrayList<>());
    }

    public Optional<List<Enrollment>> update(RequestEnrollmentDto requestDto, Integer id) throws SQLException {
        String sql = SqlConstants.UPDATE;
        log.info("Виконується update: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_UPDATE;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forUpdate(id,requestDto);
        List<Enrollment> enrollments = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return enrollments.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(enrollments);
    }

    public Optional<List<Enrollment>> deleteById(Integer studentId, Integer offeringId) throws SQLException {
        String sql = SqlConstants.DELETE;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_DELETE_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCompoundId(studentId,offeringId);
        List<Enrollment> enrollments = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return enrollments.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(enrollments);
    }

    private static List<Enrollment> executeSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction, MapperExecutor mapperExecutor) throws SQLException {
        List<Enrollment> result;
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

    private static List<Enrollment> createCoursesList(ResultSet rs, MapperExecutor mapperExecutor) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        while (rs.next()) {
            Enrollment enrollment = mapperExecutor.execute(rs);
            enrollments.add(enrollment);
        }
        return enrollments.isEmpty() ? new ArrayList<>() : enrollments;
    }

    private static int executeBatchSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction) throws SQLException {
        int totalRowsAffected = 0;

        try (Connection conn = DbConnectionProvider.getConnection()) {
            // 1. Отключаем авто-коммит для транзакционности пакета
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                log.info("Created PreparedStatement for Batch");
                stmtConfigurationAction.setValues(stmt, sqlParams);
                log.info("Set batch values in stmt");
                // Выполняем пакетный запрос одной командой в БД
                int[] resultCounts = stmt.executeBatch();
                // Фиксируем транзакцию в PostgreSQL
                conn.commit();
                log.info("Batch transaction committed successfully");

                // Считаем общее количество добавленных строк
                for (int count : resultCounts) {
                    if (count >= 0) totalRowsAffected += count;
                        // В некоторых драйверах успешный INSERT без подсчета возвращает SUCCESS_NO_INFO (-2)
                    else if (count == java.sql.Statement.SUCCESS_NO_INFO) totalRowsAffected++;
                }

            } catch (SQLException e) {
                // Если упал хотя бы один инсерт (критическая ошибка), откатываем ВСЕХ студентов
                conn.rollback();
                log.error("Batch failed. Transaction rolled back.", e);
                throw e;
            } finally {
                // Возвращаем коннект в дефолтное состояние перед закрытием (хороший тон для Connection Pool)
                conn.setAutoCommit(true);
            }
        }
        return totalRowsAffected;
    }

}





