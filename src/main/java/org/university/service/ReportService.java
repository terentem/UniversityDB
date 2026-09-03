package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.repository.ReportRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository repository;


    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, String>> doReport(String reportTitle, Map<String, String[]> queryParameters) throws SQLException {
        return repository.doReport(reportTitle, queryParameters);
    }

}



