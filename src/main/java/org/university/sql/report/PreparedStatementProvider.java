package org.university.sql.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementProvider {

    public PreparedStatementProvider() throws SQLException {
    }

    public static PreparedStatement createStmt(String sql, Object[] scriptValues, Connection conn, String orderTitle) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        switch (orderTitle) {
            case "STUDENTS_BY_OFFER_ID" -> {
            }
            default -> {
                ;
            }
        }
        return stmt;
    }
}
