package org.university.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Enrollment;
import org.university.service.EnrollmentService;
import org.university.web.dto.enrollment.RequestEnrollmentDto;
import org.university.web.dto.enrollment.ResponseEnrollmentDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentController {


    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    public List<ResponseEnrollmentDto> create(RequestEnrollmentDto requestDto) throws IOException, SQLException {
        List<Enrollment> result = enrollmentService.create(requestDto);
        List<ResponseEnrollmentDto> getResponseDto = result.stream().map(ResponseEnrollmentDto::toReadPostPutDeleteDto).toList();
        log.info("POST http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseEnrollmentDto> createAll(RequestEnrollmentDto requestDto) throws IOException, SQLException {
        List<Enrollment> result = enrollmentService.createAll(requestDto);
        List<ResponseEnrollmentDto> getResponseDto = result.stream().map(ResponseEnrollmentDto::toReadPostPutDeleteDto).toList();
        log.info("POST http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseEnrollmentDto> update(RequestEnrollmentDto requestDto, Integer studentId, Integer offeringId) throws IOException, SQLException {
        List<Enrollment> result = enrollmentService.update(requestDto, studentId, offeringId);
        List<ResponseEnrollmentDto> getResponseDto = result.stream().map(ResponseEnrollmentDto::toReadPostPutDeleteDto).toList();
        log.info("PUT http response= {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseEnrollmentDto> delete(Integer studentId, Integer offeringId) throws IOException, SQLException {
        List<Enrollment> result = enrollmentService.delete(studentId, offeringId);
        List<ResponseEnrollmentDto> getResponseDto = result.stream().map(ResponseEnrollmentDto::toReadPostPutDeleteDto).toList();
        log.info("DELETE http response= {}", getResponseDto);
        return getResponseDto;
    }
}
