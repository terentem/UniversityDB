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
import org.university.web.dto.GetProfessorDto;
import org.university.web.dto.PostProfessorDto;
import org.university.web.dto.ShortHttpGetRequestDto;
import org.university.web.dto.ShortHttpPostRequestDto;
import org.university.web.model.RequestParameter;
import org.university.web.utilities.HttpBodyValidator;
import org.university.web.utilities.ResponseDataProvider;

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

    public void readProfessors(ShortHttpGetRequestDto httpRequestDto, HttpServletResponse response) throws IOException {
        try {
            //Validation of request parameters
            log.info("Input data: Request request={} ", httpRequestDto);
            String params;
            String pathVariableName = httpRequestDto.pathVariableName();
            int pathLength = httpRequestDto.fullPathLength();
            log.info("pathLength={} ", pathLength);
            if (pathLength == 4) {
                if (pathVariableName.equals(RequestParameter.ID.value()) ||
                        pathVariableName.equals(RequestParameter.NAME.value()) ||
                        pathVariableName.equals(RequestParameter.EMAIL.value())) {
                    params = httpRequestDto.pathVariableValue();
                } else {
                    log.error("Unknown path variable name detected: {}", pathVariableName);
                    throw new IllegalArgumentException("Unknown parameter : " + pathVariableName);
                }
            } else {
                log.error("Empty id or name in request");
                throw new IllegalArgumentException("Empty value of " + pathVariableName);
            }
            log.info("Extracted pathVariableValue=: {}", params);

            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.getProfessors(params, pathVariableName);
            List<GetProfessorDto> getProfessorDto = ResponseDataProvider.createGetHttpResponseDto(optionalResult);

            // Serialization into JSON for response
            String jsonResult = objectMapper.writeValueAsString(getProfessorDto);
            response.getWriter().write(jsonResult);
            response.getWriter().flush();
            log.info("Reply jsonResult result {}", jsonResult);

        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            log.error("Error message={}, errorCode={}", ex.getMessage(), ex);
        }
    }

    public void createProfessor(ShortHttpPostRequestDto request, HttpServletResponse response) throws IOException {
        String params=HttpBodyValidator.checkPostHttpBody(request);

        try {
            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.createProfessor(params);

            //Create and send response
            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                List<PostProfessorDto> postProfessorDto = ResponseDataProvider.createPostHttpResponseDto(optionalResult);
                log.info("POST http response= {}",postProfessorDto);
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

    public void updateProfessor(ShortHttpPostRequestDto request, HttpServletResponse response) throws IOException {
        String params=HttpBodyValidator.checkPUTtHttpBody(request);
        try {
            //Call ProfessorService
            Optional<List<Professor>> optionalResult = professorService.updateProfessor(params);

            //Create and send response
            if (optionalResult.isPresent()) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 ok
                List<PostProfessorDto> postProfessorDto = ResponseDataProvider.createPostHttpResponseDto(optionalResult);
                log.info("PUT http response= {}",postProfessorDto);
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
