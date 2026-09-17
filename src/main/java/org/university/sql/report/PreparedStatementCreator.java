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

public class PreparedStatementCreator {
    private static final Logger log = LoggerFactory.getLogger(PreparedStatementCreator.class);

    public PreparedStatementCreator() throws SQLException {
    }

    public  PreparedStatement createStmt(String sql, Connection conn, RequestReportDto requestReportDto, String orderTitle) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        //set values to stmt according to reportId
        switch (orderTitle) {
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

