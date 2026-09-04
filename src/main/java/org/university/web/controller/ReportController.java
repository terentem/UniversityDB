package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.service.ReportService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public List<Map<String, String>> doReport(String reportTitle, Map<String, String[]> queryParameters) throws IOException, SQLException {
        List<Map<String, String>> result = reportService.doReport(reportTitle, queryParameters);
        log.info("GET reply  {}", result);
        return result;
    }

}
