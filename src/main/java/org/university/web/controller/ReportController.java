package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.service.ReportService;
import org.university.web.dto.report.RequestReportDto;
import org.university.web.dto.report.ResponseReportDto;

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

    public List<ResponseReportDto> doReport(String reportTitle, RequestReportDto requestReportDto) throws IOException, SQLException {
        List<ResponseReportDto> result = reportService.doReport(reportTitle, requestReportDto);
        log.info("GET reply  {}", result);
        return result;
    }

}
