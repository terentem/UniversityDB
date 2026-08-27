package org.university.context;


import org.university.repository.EnrollmentRepository;
import org.university.repository.OfferingRepository;
import org.university.service.EnrollmentService;
import org.university.service.OfferingService;
import org.university.web.controller.EnrollmentController;
import org.university.web.controller.OfferingController;

public class EnrollmentContext {

    EnrollmentRepository repository = new EnrollmentRepository();
    EnrollmentService service = new EnrollmentService(repository);
    EnrollmentController controller = new EnrollmentController(service);

    public EnrollmentRepository getRepository() {
        return repository;
    }

    public EnrollmentService getService() {
        return service;
    }

    public EnrollmentController getController() {
        return controller;
    }

}