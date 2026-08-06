package org.university.web.dto.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.StudentContext;
import org.university.web.dto.professor.RequestProfessorDto;

import java.io.IOException;

public class RequestStudentMapper {
    private static final Logger log = LoggerFactory.getLogger(RequestStudentMapper.class);
    static ObjectMapper objectMapper= StudentContext.objectMapper;


    public static Integer getPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? Integer.parseInt(path.split("/")[2]) : null;
    }

    public static RequestProfessorDto mapToRequestProfessorDto(HttpServletRequest request) throws IOException {
        log.info("Start mapping from http body into RequestProfessorDto");
        return objectMapper.readValue(
                request.getInputStream(),
                RequestProfessorDto.class
        );
    }

    public static RequestStudentDto mapToRequestStudentDto(HttpServletRequest request) throws IOException {
        log.info("Start mapping from http body into RequestProfessorDto");
        return objectMapper.readValue(
                request.getInputStream(),
                RequestStudentDto.class
        );
    }

}



