package org.university.context;


import org.university.repository.EnrollmentRepository;
import org.university.repository.OfferingRepository;
import org.university.repository.StudentRepository;
import org.university.service.EnrollmentService;
import org.university.service.OfferingService;
import org.university.service.StudentService;
import org.university.web.controller.EnrollmentController;

public class EnrollmentContext {

    EnrollmentRepository repository = new EnrollmentRepository();
    StudentRepository studentRepository=new StudentRepository();
    OfferingRepository offeringRepository=new OfferingRepository();
    StudentService studentService=new StudentService(studentRepository);
    OfferingService offeringService=new OfferingService(offeringRepository);
    EnrollmentService service = new EnrollmentService(repository,studentService,offeringService);

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