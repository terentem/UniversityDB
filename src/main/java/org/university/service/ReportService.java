package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.repository.ReportRepository;
import org.university.web.dto.report.ReportDtoMapper;
import org.university.web.dto.report.RequestReportDto;
import org.university.web.dto.report.ResponseReportDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository repository;


    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<ResponseReportDto> doReport(String reportTitle, RequestReportDto requestReportDto) throws SQLException {
        return repository.doReport(reportTitle, requestReportDto);
    }

}



