package org.university.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.service.ProfessorService;
import org.university.web.dto.InputProfessorDto;
import org.university.web.dto.OutputProfessorDto;
import org.university.web.utilities.ResponseProfessorMapper;

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

    public void readProfessors(String pathVariable, HttpServletResponse response) throws IOException {
        try {
            log.info("provided pathVariableValue=: {}", pathVariable);

            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.getProfessors(pathVariable);
            List<OutputProfessorDto> getProfessorDto = ResponseProfessorMapper.createGetHttpResponseDto(optionalResult);

            // Serialization into JSON for response
            String jsonResult = objectMapper.writeValueAsString(getProfessorDto);
            response.getWriter().write(jsonResult);
            response.getWriter().flush();
            log.info("Reply jsonResult result {}", jsonResult);
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    public void createProfessor(InputProfessorDto professorDto, HttpServletResponse response) throws IOException {
        try {
            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.createProfessor(professorDto);
            //Create and send response
            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                List<OutputProfessorDto> postProfessorDto = ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
                log.info("POST http response= {}", postProfessorDto);
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format. Expected: name,email,department_id\"}");
            }
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    public void updateProfessor(InputProfessorDto professorDto, HttpServletResponse response) throws IOException {
        try {
            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.updateProfessor(professorDto);

            //Create and send response
            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 ok
                List<OutputProfessorDto> postProfessorDto = ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
                log.info("PUT http response= {}", postProfessorDto);
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format. Expected: name,email,department_id\"}");
            }
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }

    public void deleteProfessor(InputProfessorDto professorDto, HttpServletResponse response) throws IOException {
        try {
            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.deleteProfessor(professorDto);

            //Create and send response
            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED); // 200 ok
                List<OutputProfessorDto> postProfessorDto = ResponseProfessorMapper.createPostPutDeleteHttpResponseDto(optionalResult);
                log.info("PUT http response= {}", postProfessorDto);
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format. Expected: name,email,department_id\"}");
            }
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
