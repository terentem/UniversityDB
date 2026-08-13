package org.university.context;


import org.university.repository.StudentRepository;
import org.university.service.StudentService;
import org.university.web.controller.StudentController;

public class StudentContext {


    StudentRepository repository = new StudentRepository();
    StudentService studentService = new StudentService(repository);
    StudentController studentController = new StudentController(studentService);

    public StudentRepository getRepository() {
        return repository;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public StudentController getStudentController() {
        return studentController;
    }


}