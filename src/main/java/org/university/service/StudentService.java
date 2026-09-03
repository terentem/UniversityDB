package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Student;
import org.university.repository.StudentRepository;
import org.university.web.dto.student.RequestStudentDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> get(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll().orElse(Collections.emptyList());
        } else {
            log.info("id = params={}", id);
            return repository.findById(id).orElse(Collections.emptyList());
        }
    }

    public List<Student> getByInterestId(Integer id) throws SQLException {
        return repository.findByInterestId(id).orElse(Collections.emptyList());
    }

    public List<Student> create(RequestStudentDto studentDto) throws SQLException {
        return repository.create(studentDto).orElse(Collections.emptyList());
    }

    public List<Student> update(RequestStudentDto studentDto, Integer pathVariable) throws SQLException {
        return repository.update(studentDto, pathVariable).orElse(Collections.emptyList());
    }

    public List<Student> delete(Integer id) throws SQLException {
        return repository.deleteById(id).orElse(Collections.emptyList());
    }
}



