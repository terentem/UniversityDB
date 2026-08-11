package org.university;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.university.repository.StudentRepository;
import org.university.service.StudentService;
import org.university.web.controller.StudentController;

public class StudentContext {
    public static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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