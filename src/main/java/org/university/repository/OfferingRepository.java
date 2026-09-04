package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Offering;
import org.university.repository.mapper.offering.MapperConstants;
import org.university.repository.mapper.offering.MapperExecutor;
import org.university.repository.mapper.offering.StatementValueSetter;
import org.university.sql.offering.SqlConstants;
import org.university.sql.offering.SqlParameters;
import org.university.web.dto.offering.RequestOfferingDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfferingRepository {

    private static final Logger log = LoggerFactory.getLogger(OfferingRepository.class);

    public Optional<List<Offering>> findById(Integer id) throws SQLException {
        String sql = SqlConstants.FIND_BY_ID;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Offering> offerings = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return offerings.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(offerings);
    }

    public Optional<List<Offering>> findAll() throws SQLException {
        String sql = SqlConstants.FIND_ALL;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_FIND_BY_ALL;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forAll();
        List<Offering> offerings = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return offerings.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(offerings);
    }

    public Optional<List<Offering>> create(RequestOfferingDto requestDto) throws SQLException {
        String sql = SqlConstants.INSERT;
        log.info("Виконується інсерт: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_INSERT;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forCreate(requestDto);
        List<Offering> offerings = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return offerings.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(offerings);
    }

    public Optional<List<Offering>> update(RequestOfferingDto requestDto, Integer id) throws SQLException {
        String sql = SqlConstants.UPDATE;
        log.info("Виконується update: sql {} ", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_UPDATE;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        SqlParameters paramsForStmtConfiguration = SqlParameters.forUpdate(id,requestDto);
        List<Offering> offerings = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return offerings.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(offerings);
    }

    public Optional<List<Offering>> deleteById(Integer id) throws SQLException {
        String sql = SqlConstants.DELETE;
        log.info("script={}", sql);
        StatementValueSetter stmtConfigurationAction = SqlConstants.ACTION_FOR_DELETE_BY_ID;
        MapperExecutor mapperExecutor = MapperConstants.CONSTANT;
        log.info("Setters for sql-template= {}", stmtConfigurationAction);
        SqlParameters paramsForStmtConfiguration = SqlParameters.forId(id);
        List<Offering> offerings = executeSql(sql, paramsForStmtConfiguration, stmtConfigurationAction, mapperExecutor);
        return offerings.isEmpty() ? Optional.of(new ArrayList<>()) : Optional.of(offerings);
    }

    private static List<Offering> executeSql(String sql, SqlParameters sqlParams, StatementValueSetter stmtConfigurationAction, MapperExecutor mapperExecutor) throws SQLException {
        List<Offering> result;
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

    private static List<Offering> createCoursesList(ResultSet rs, MapperExecutor mapperExecutor) throws SQLException {
        List<Offering> offerings = new ArrayList<>();
        while (rs.next()) {
            Offering offering = mapperExecutor.execute(rs);
            offerings.add(offering);
        }
        return offerings.isEmpty() ? new ArrayList<>() : offerings;
    }
}





