package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Student;
import org.university.service.StudentService;
import org.university.web.dto.student.RequestStudentDto;
import org.university.web.dto.student.ResponseStudentDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentController {


    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<ResponseStudentDto> read(Integer id) throws IOException, SQLException {
        List<Student> result = studentService.get(id);
        List<ResponseStudentDto> getResponseDto = result.stream().map(ResponseStudentDto::toGteDto).toList();
        log.info("GET reply  {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseStudentDto> create(RequestStudentDto requestStudentDto) throws IOException, SQLException {
        List<Student> result = studentService.create(requestStudentDto);
        List<ResponseStudentDto> getResponseDto = result.stream().map(ResponseStudentDto::toPostPutDeleteDto).toList();
        log.info("POST http response= {}", getResponseDto);

        return getResponseDto;

    }

    public List<ResponseStudentDto> update(RequestStudentDto requestStudentDto, Integer pathVariable) throws IOException, SQLException {
        List<Student> result = studentService.update(requestStudentDto, pathVariable);
        List<ResponseStudentDto> getResponseDto = result.stream().map(ResponseStudentDto::toPostPutDeleteDto).toList();
        log.info("PUT http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseStudentDto> delete(Integer id) throws IOException, SQLException {
        List<Student> result = studentService.delete(id);
        List<ResponseStudentDto> getResponseDto = result.stream().map(ResponseStudentDto::toPostPutDeleteDto).toList();
        log.info("DELETE http response= {}", getResponseDto);
        return getResponseDto;
    }


}
