package org.university.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.service.ProfessorService;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.dto.professor.ResponseProfessorDto;
import org.university.web.utilities.professor.ProfessorValidator;
import org.university.web.dto.student.RequestStudentMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProfessorController {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public void readProfessors(Integer id, HttpServletResponse response) throws IOException, SQLException {
        Optional<List<Professor>> optionalResult = professorService.getProfessors(id);
        List<ResponseProfessorDto> getProfessorDto = ProfessorValidator.ResponseProfessorMapper.createGetHttpResponseDto(optionalResult);
        log.info("GET reply  {}", getProfessorDto);
        responseSender(getProfessorDto, response);
    }

    public void createProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        RequestProfessorDto requestProfessorDto = RequestStudentMapper.mapToRequestProfessorDto(request);
        Optional<List<Professor>> optionalResult = professorService.createProfessor(requestProfessorDto);
        List<ResponseProfessorDto> postProfessorDto = ProfessorValidator.ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("POST http response= {}", postProfessorDto);
        responseSender(postProfessorDto, response);
    }

    public void updateProfessor(HttpServletRequest request, HttpServletResponse response, Integer pathVariable) throws IOException, SQLException {
        RequestProfessorDto requestProfessorDto = RequestStudentMapper.mapToRequestProfessorDto(request);
        Optional<List<Professor>> optionalResult = professorService.updateProfessor(requestProfessorDto, pathVariable);
        List<ResponseProfessorDto> postProfessorDto = ProfessorValidator.ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("PUT http response= {}", postProfessorDto);
        responseSender(postProfessorDto, response);
    }

    public void deleteProfessor(Integer id, HttpServletResponse response) throws IOException, SQLException {
        Optional<List<Professor>> optionalResult = professorService.deleteProfessor(id);
        List<ResponseProfessorDto> postProfessorDto = ProfessorValidator.ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
        log.info("DELETE http response= {}", postProfessorDto);
        responseSender(postProfessorDto, response);
    }

    public void responseSender(List<ResponseProfessorDto> postProfessorDto, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (postProfessorDto.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{Client not found.}");
            } else if (postProfessorDto.size() > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 ok
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
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
