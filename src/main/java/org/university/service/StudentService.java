package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.domain.model.Student;
import org.university.repository.StudentRepository;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.dto.student.RequestStudentDto;
import org.university.web.utilities.professor.ProfessorValidator;
import org.university.web.utilities.student.StudentValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Optional<List<Student>> get(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll();
        } else {
            log.info("id = params={}", id);
            return repository.findById(id);
        }
    }

    public Optional<List<Student>> create(RequestStudentDto studentDto) throws SQLException {
        StudentValidator.checkPostHttpBody(studentDto);
        return repository.create(studentDto);
    }

    public Optional<List<Student>> update(RequestStudentDto studentDto, Integer pathVariable) throws SQLException {
        StudentValidator.checkPutHttpBody(studentDto);
        return repository.updateProfessor(studentDto, pathVariable);
    }

    public Optional<List<Student>> delete(Integer id) throws SQLException {
        return repository.deleteById(id);
    }
}



