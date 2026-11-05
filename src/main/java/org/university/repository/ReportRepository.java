package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.sql.report.Stmt;
import org.university.web.dto.report.RequestReportDto;
import org.university.web.dto.report.ResponseReportDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReportRepository {

    private static final Logger log = LoggerFactory.getLogger(ReportRepository.class);
    private final Stmt preparedStatementStmt;

    public ReportRepository(Stmt preparedStatementCreator) {
        this.preparedStatementStmt = preparedStatementCreator;
    }
    public List<ResponseReportDto> doReport(String reportTitle, RequestReportDto requestReportDto) throws SQLException {

        try (
                Connection conn = DbConnectionProvider.getConnection();
                PreparedStatement stmt = preparedStatementStmt.createStmt(conn, requestReportDto, reportTitle);) {
            List<ResponseReportDto> result = preparedStatementStmt.executeStmt(stmt, reportTitle);
            return result;
        }

    }
}






