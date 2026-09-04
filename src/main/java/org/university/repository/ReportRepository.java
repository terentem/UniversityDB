package org.university.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.sql.report.PreparedStatementProvider;
import org.university.sql.report.SqlConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportRepository {

    private static final Logger log = LoggerFactory.getLogger(ReportRepository.class);

    public List<Map<String, String>> doReport(String reportTitle, Map<String, String[]> queryParameters) throws SQLException {
        SqlConfiguration config = SqlConfiguration.getById(reportTitle);
        String sql = config.getSql();
        Object[] scriptValues = config.extractParams(queryParameters);
        log.info("script={}", sql);
        String reportEnumTitle = config.name();
        try (
                Connection conn = DbConnectionProvider.getConnection();
                PreparedStatement stmt = PreparedStatementProvider.createStmt(sql, scriptValues, conn, reportEnumTitle);) {
            ResultSet rs = stmt.executeQuery();

            List<Map<String, String>> result = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
            return result;
        }
    }
}





