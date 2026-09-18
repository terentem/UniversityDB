package org.university.sql.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.dto.report.ReportDtoMapper;
import org.university.web.dto.report.RequestReportDto;
import org.university.web.dto.report.ResponseReportDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stmt {
    private static final Logger log = LoggerFactory.getLogger(Stmt.class);

    public Stmt() throws SQLException {
    }

    public  PreparedStatement createStmt( Connection conn, RequestReportDto requestReportDto, String reportTitle) throws SQLException {
        SqlScripts config = SqlScripts.getById(reportTitle);
        String sql = config.getSql();
        log.info("script={}", sql);
        String reportEnumTitle = config.name();

        PreparedStatement stmt = conn.prepareStatement(sql);
        //set values to stmt according to reportId
        switch (reportEnumTitle) {
            case "STUDENTS_BY_OFFER_ID" -> {
            }
            case "STUDENTS_GROUPED_BY_OFFERING_ID_WITH_FILTER_GRADE" -> {
                stmt.setString(1, requestReportDto.grade());
            }
            default -> {
                ;
            }
        }
        return stmt;
    }

    public  List<ResponseReportDto> executeStmt(PreparedStatement stmt, String pathVariable) {
        List<ResponseReportDto> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ResponseReportDto dto = ReportDtoMapper.mapToResponseDto(pathVariable, rs);
                result.add(dto);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса отчета", e);
        }
    }
}

