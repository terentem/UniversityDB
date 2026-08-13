package org.university.context;


import org.university.repository.CourseRepository;

import org.university.service.CourseService;

import org.university.web.controller.CourseController;

public class CourseContext {

    CourseRepository repository = new CourseRepository();
    CourseService service = new CourseService(repository);
    CourseController controller = new CourseController(service);

    public CourseRepository getRepository() {
        return repository;
    }

    public CourseService getStudentService() {
        return service;
    }

    public CourseController getStudentController() {
        return controller;
    }


}