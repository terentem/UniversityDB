package org.university.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.university.StudentContext;
import org.university.domain.model.Student;
import org.university.service.StudentService;
import org.university.web.dto.student.RequestStudentDto;

import org.university.web.dto.student.ResponseStudentDto;

import org.university.web.dto.student.RequestStudentMapper;
import org.university.web.dto.student.ResponseStudentMapper;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentController {


    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    ObjectMapper objectMapper= StudentContext.objectMapper;

    public StudentController(StudentService studentService) {
        this.studentService =studentService;

    }

    public void read(Integer id, HttpServletResponse response) throws IOException, SQLException {
        Optional<List<Student>> optionalResult = studentService.get(id);
        List<ResponseStudentDto> getStudentDto = ResponseStudentMapper.createGetHttpResponseDto(optionalResult);
        log.info("GET reply  {}", getStudentDto);
        responseSender(getStudentDto, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        RequestStudentDto requestStudentDto = RequestStudentMapper.mapToRequestStudentDto(request);
        Optional<List<Student>> optionalResult = studentService.create(requestStudentDto);
        List<ResponseStudentDto> postStudentDto = ResponseStudentMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("POST http response= {}", postStudentDto);
        responseSender(postStudentDto, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response, Integer pathVariable) throws IOException, SQLException {
        RequestStudentDto requestStudentDto = RequestStudentMapper.mapToRequestStudentDto(request);
        Optional<List<Student>> optionalResult = studentService.update(requestStudentDto, pathVariable);
        List<ResponseStudentDto> postStudentDto = ResponseStudentMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("PUT http response= {}", postStudentDto);
        responseSender(postStudentDto, response);
    }

    public void delete(Integer id, HttpServletResponse response) throws IOException, SQLException {
        Optional<List<Student>> optionalResult = studentService.delete(id);
        List<ResponseStudentDto> postStudentDto = ResponseStudentMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("DELETE http response= {}", postStudentDto);
        responseSender(postStudentDto, response);
    }

    public void responseSender(List<ResponseStudentDto> postStudentDto, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (postStudentDto.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{Client not found.}");
            } else if (postStudentDto.size() > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 ok
                response.getWriter().write(objectMapper.writeValueAsString(postStudentDto));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format.}");
            }
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
