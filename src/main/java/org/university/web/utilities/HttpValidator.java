package org.university.web.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.model.RequestParameter;

import java.util.Map;
import java.util.Optional;

public class HttpValidator {
    private static final Logger log = LoggerFactory.getLogger(HttpValidator.class);

    public static String checkPostHttpBody(Map<String, String> body) {
        String profNameForService = Optional.ofNullable(body.get(RequestParameter.NAME.value()))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: NAME"));

        String profEmailForService = Optional.ofNullable(body.get(RequestParameter.EMAIL.value()))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: EMAIL"));

        Integer profDepartmentForService = Optional.ofNullable(Integer.parseInt(body.get(RequestParameter.DEPARTMENT.value())))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: DEPARTMENT"));

        String params = profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        log.info("Successful http body validation");
        return params;
    }

    public static String checkPUTtHttpBody(Map<String, String> body) {
        Integer profIdForService = Optional.ofNullable(Integer.parseInt(body.get(RequestParameter.ID.value())))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: ID"));

        String profNameForService = Optional.ofNullable(body.get(RequestParameter.NAME.value()))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: NAME"));

        String profEmailForService = Optional.ofNullable(body.get(RequestParameter.EMAIL.value()))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: EMAIL"));

        Integer profDepartmentForService = Optional.ofNullable(Integer.parseInt(body.get(RequestParameter.DEPARTMENT.value())))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: DEPARTMENT"));

        String params = profIdForService + "," + profNameForService + "," + profEmailForService + "," + profDepartmentForService;
        log.info("Отриманий параметр = {} .Method = {}. FullPath={}  ", params);
        return params;
    }

    public static void checkPostPutHttpPath(String path) {
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

    public static void checkDeletePath(String path) {
        int pathLength = path.split("/").length;
        if (pathLength == 2) {
            log.info("Success path check. path.length= = {} ", pathLength);
        } else {
            log.error("Illigal path {}", path);
            throw new IllegalArgumentException("Illigal path" + path);
        }
    }

    public static String checkDeletetHttpBody(Map<String, String> body) {
        Integer profIdForService = Optional.ofNullable(Integer.parseInt(body.get(RequestParameter.ID.value())))
                .orElseThrow(() -> new IllegalArgumentException("Missing required parameter: ID"));
        log.info("Отриманий параметр = {} .Method = {}. FullPath={}  ", profIdForService);
        return String.valueOf(profIdForService);
    }


}
