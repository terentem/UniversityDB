package org.university.context;


import org.university.repository.ReportRepository;
import org.university.service.ReportService;
import org.university.sql.report.Stmt;
import org.university.web.controller.ReportController;

import java.sql.SQLException;

public class ReportContext {
    Stmt preparedStatementStmt = new Stmt();
    ReportRepository repository = new ReportRepository(preparedStatementStmt);
    ReportService service = new ReportService(repository);
    ReportController controller = new ReportController(service);

    public ReportContext() throws SQLException {
    }

    public ReportRepository getRepository() {
        return repository;
    }

    public ReportController getReportController() {
        return controller;
    }

}