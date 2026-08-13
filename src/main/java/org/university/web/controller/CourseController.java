package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Course;

import org.university.service.CourseService;

import org.university.web.dto.course.RequestCourseDto;
import org.university.web.dto.course.ResponseCourseDto;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseController {


    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<ResponseCourseDto> read(Integer id) throws IOException, SQLException {
        List<Course> result = courseService.get(id);
        List<ResponseCourseDto> getResponseDto = result.stream().map(ResponseCourseDto::toGteDto).toList();
        log.info("GET reply  {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseCourseDto> create(RequestCourseDto requestDto) throws IOException, SQLException {
        List<Course> result = courseService.create(requestDto);
        List<ResponseCourseDto> getResponseDto = result.stream().map(ResponseCourseDto::toPostPutDeleteDto).toList();
        log.info("POST http response= {}", getResponseDto);

        return getResponseDto;

    }

    public List<ResponseCourseDto> update(RequestCourseDto requestDto, Integer pathVariable) throws IOException, SQLException {
        List<Course> result = courseService.update(requestDto, pathVariable);
        List<ResponseCourseDto> getResponseDto = result.stream().map(ResponseCourseDto::toPostPutDeleteDto).toList();
        log.info("PUT http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseCourseDto> delete(Integer id) throws IOException, SQLException {
        List<Course> result = courseService.delete(id);
        List<ResponseCourseDto> getResponseDto = result.stream().map(ResponseCourseDto::toPostPutDeleteDto).toList();
        log.info("DELETE http response= {}", getResponseDto);
        return getResponseDto;
    }


}
