package org.university.context;


import org.university.repository.ReportRepository;
import org.university.service.ReportService;
import org.university.sql.report.PreparedStatementCreator;
import org.university.web.controller.ReportController;

import java.sql.SQLException;

public class ReportContext {
    PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator();
    ReportRepository repository = new ReportRepository(preparedStatementCreator);
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