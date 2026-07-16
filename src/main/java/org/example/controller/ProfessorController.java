package org.example.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.dto.GetProfessorDto;
import org.example.domain.dto.PostProfessorDto;
import org.example.domain.model.Professor;
import org.example.service.ProfessorService;
import org.example.web.Request;
import org.example.web.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfessorController {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    private final ProfessorService professorService;
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    //@Override
    public void readProfessors(Request request, HttpServletResponse response) throws IOException {
        try {
            log.info("Input data: Request request={} ",request);
            String params = request.getParameter(RequestParameter.NAME)
                    .or(() -> request.getParameter(RequestParameter.EMAIL))

                    .orElseThrow(() -> {
                        log.error("Unknown parameter in enum of parameters.");
                        return new IllegalArgumentException("Unknown parameter in query.");
                    });
            log.info("Successfully extracted parameter from params.");
            log.info("Call \"ProfessorService\" class, method \"getProfessors\".");
            Optional<List<Professor>> optionalResult = professorService.getProfessors(params);
            List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
            List<GetProfessorDto> getProfessorDto = professorsList.stream()
                    .map(p -> new GetProfessorDto(p.name(), p.email()))
                    .toList();
            // Серіалізуємо вже чистий List в JSON
            String jsonResult = objectMapper.writeValueAsString(getProfessorDto);
            response.getWriter().write(jsonResult);
            response.getWriter().flush();
            log.info("Reply from \"ProfessorController\", method \"readProfessors\" with result {}", jsonResult);

        } catch (SQLException ex) {
            // Замість RuntimeException краще віддати клієнту 500 помилку
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            log.error("Error message={}, errorCode={}",ex.getMessage(), ex);
        }
    }

    //@Override
    public void createProfessor(Request request, HttpServletResponse response) throws IOException {

        String profNameForService = request.getParameter(RequestParameter.NAME)
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: NAME"));

        String profEmailForService = request.getParameter(RequestParameter.EMAIL)
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: EMAIL"));

        String profDepartmentForService = request.getParameter(RequestParameter.DEPARTMENT)
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: DEPARTMENT"));
        String params = profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        System.out.println("Отриманий параметр = " + params);

        try {
            Optional<List<Professor>> optionalResult = professorService.createProfessor(params);

            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                List<Professor> professorList = optionalResult.orElse(Collections.emptyList());
                List<PostProfessorDto> postProfessorDto = professorList.stream()
                        .map(p -> new PostProfessorDto(p.id(), p.name(), p.email(), p.departmentId()))
                        .toList();
                response.getWriter().write(objectMapper.writeValueAsString(postProfessorDto));
            } else {
                // Якщо paramsArr.length != 3 або сталася інша помилка валідації
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("{\"error\": \"Invalid parameters count or format. Expected: name,email,department_id\"}");
            }
        } catch (SQLException ex) {
            // Замість RuntimeException краще віддати клієнту 500 помилку
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
