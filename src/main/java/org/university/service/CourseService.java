package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Course;

import org.university.repository.CourseRepository;

import org.university.web.dto.course.RequestCourseDto;


import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> get(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll().orElse(Collections.emptyList());
        } else {
            log.info("id = params={}", id);
            return repository.findById(id).orElse(Collections.emptyList());
        }
    }

    public List<Course> create(RequestCourseDto courseDto) throws SQLException {
        return repository.create(courseDto).orElse(Collections.emptyList());
    }

    public List<Course> update(RequestCourseDto courseDto, Integer pathVariable) throws SQLException {
        return repository.update(courseDto, pathVariable).orElse(Collections.emptyList());
    }

    public List<Course> delete(Integer id) throws SQLException {
        return repository.deleteById(id).orElse(Collections.emptyList());
    }
}



