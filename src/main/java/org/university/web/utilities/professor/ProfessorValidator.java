package org.university.web.utilities.professor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Professor;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.professor.RequestProfessorDto;
import org.university.web.dto.professor.ResponseProfessorDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfessorValidator {
    private static final Logger log = LoggerFactory.getLogger(ProfessorValidator.class);

    public static void checkGetPath(String path) {
        int pathLength = path.split("/").length;
        if (pathLength == 3) {
            log.info("Success path check. path.length= = {} ", pathLength);
        } else if (pathLength == 2) {
            log.info("Success path check. path.length= {} ", pathLength);
        } else {
            log.error("Illigal path {}", path);
            throw new IllegalArgumentException("Illigal path" + path);
        }
    }

    public static String checkPostHttpBody(RequestProfessorDto body) {
        if (body.name() == null || body.name().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: NAME");
        }

        if (body.email() == null || body.email().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: EMAIL");
        }

        if (body.departmentId() == null) {
            throw new IllegalArgumentException("Missing required parameter: DEPARTMENTID");
        }
        String paramsInBody = body.name() + ", " + body.email() + ", " + body.departmentId();
        return paramsInBody;
    }

       public static void checkPostHttpPath(String path) {
        log.info("Path for check = {}", path);
        String[] pathArr = path.split("/");
        int pathLength = pathArr.length;
        if (pathLength != 2) {
            log.error("Illigal path {}", path);
            log.info("path length = {}, path[0]= {}, path[1]= {}", pathLength, pathArr[0], pathArr[1]);
            throw new IllegalArgumentException("Not valid path {}" + path);
        } else {
            log.info("path length = {}, path[0]= {}, path[1]= {}", pathLength, pathArr[0], pathArr[1]);
        }
    }

    public static String checkPutHttpBody(RequestProfessorDto body) {

        if (body.name() == null || body.name().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: NAME");
        }

        if (body.email() == null || body.email().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: EMAIL");
        }

        if (body.departmentId() == null) {
            throw new IllegalArgumentException("Missing required parameter: DEPARTMENTID");
        }
        String paramsInBody = body.name() + ", " + body.email() + ", " + body.departmentId();
        log.info("Check params in http body: {}",paramsInBody);
        return paramsInBody;
    }

    public static void checkPutDeletePath(String path) {
        String[] pathArr = path.split("/");
        int pathLength = pathArr.length;
        if (pathLength == 3 && Integer.parseInt(pathArr[2]) > 0) {
            log.info("Success path check. path.length= = {} ", pathLength);
        } else {
            log.error("Illigal path {}, path.length= = {} ", path, pathLength);
            throw new IllegalArgumentException("Illigal path" + path);
        }
    }

    public static void checkDeletetHttpBody(Integer pathVeraible) {
        if (pathVeraible ==null) {
            log.info("Missing required parameter: ID ");
            throw new IllegalArgumentException("Missing required parameter: ID");
        }
    }

    public static class ResponseProfessorMapper {
        private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

        public static List<ResponseProfessorDto> createGetHttpResponseDto(Optional<List<Professor>> optionalResult) {
            List<Professor> professorsList = optionalResult.orElse(Collections.emptyList());
            List<ResponseProfessorDto> ResponseDto = professorsList.stream()
                    .map(p -> new ResponseProfessorDto(p.id(), p.name(), p.email(), p.department(), p.departmentId()))
                    .toList();
            return ResponseDto;
        }

        public static List<ResponseProfessorDto> createPostPutDeleteHttpResponseDto(Optional<List<Professor>> optionalResult) {
            List<Professor> professorList = optionalResult.orElse(Collections.emptyList());
            List<ResponseProfessorDto> postProfessorDto = professorList.stream()
                    .map(p -> new ResponseProfessorDto(p.id(), p.name(), p.email(), p.departmentId()))
                    .toList();
            return postProfessorDto;
        }
    }
}
