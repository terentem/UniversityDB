package org.university.web.utilities.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.dto.student.RequestStudentDto;

public class StudentValidator {
    private static final Logger log = LoggerFactory.getLogger(StudentValidator.class);

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

    public static String checkPostHttpBody(RequestStudentDto body) {
        if (body.name() == null || body.name().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: NAME");
        }

        if (body.email() == null || body.email().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: EMAIL");
        }

        if (body == null) {
            throw new IllegalArgumentException("Missing  all parameters");
        }
        String paramsInBody = body.name() + ", " + body.email() ;
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

    public static String checkPutHttpBody(RequestStudentDto body) {

        if (body.name() == null || body.name().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: NAME");
        }

        if (body.email() == null || body.email().isBlank()) {
            throw new IllegalArgumentException("Missing required parameter: EMAIL");
        }


        String paramsInBody = body.name() + ", " + body.email() ;
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
}
