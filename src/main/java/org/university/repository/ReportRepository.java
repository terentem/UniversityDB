package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.sql.report.PreparedStatementCreator;
import org.university.sql.report.SqlScripts;
import org.university.web.dto.report.RequestReportDto;
import org.university.web.dto.report.ResponseReportDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReportRepository {

    private static final Logger log = LoggerFactory.getLogger(ReportRepository.class);
    private final PreparedStatementCreator preparedStatementCreator;

    public ReportRepository(PreparedStatementCreator preparedStatementCreator) {
        this.preparedStatementCreator = preparedStatementCreator;
    }
    public List<ResponseReportDto> doReport(String reportTitle, RequestReportDto requestReportDto) throws SQLException {
        SqlScripts config = SqlScripts.getById(reportTitle);
        String sql = config.getSql();
        log.info("script={}", sql);
        String reportEnumTitle = config.name();
        try (
                Connection conn = DbConnectionProvider.getConnection();
                PreparedStatement stmt = preparedStatementCreator.createStmt(sql, conn, requestReportDto, reportEnumTitle);) {
            List<ResponseReportDto> result = preparedStatementCreator.executeStmt(stmt, reportTitle);
            return result;
        }

    }
}






