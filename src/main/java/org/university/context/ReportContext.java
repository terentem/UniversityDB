package org.university.context;


import org.university.repository.ReportRepository;
import org.university.service.ReportService;
import org.university.web.controller.ReportController;

public class ReportContext {

    ReportRepository repository = new ReportRepository();
    ReportService service = new ReportService(repository);
    ReportController controller = new ReportController(service);

    public ReportRepository getRepository() {
        return repository;
    }

    public ReportService getReportService() {
        return service;
    }

    public ReportController getReportController() {
        return controller;
    }

}