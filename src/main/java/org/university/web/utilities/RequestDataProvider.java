package org.university.web.utilities;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.web.controller.ProfessorController;
import org.university.web.dto.ShortHttpGetRequestDto;
import org.university.web.dto.ShortHttpPostRequestDto;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    public static ShortHttpGetRequestDto createGetHttpRequestDto(HttpServletRequest request) {
        if (request.getPathInfo().split("/").length > 3) {
            ShortHttpGetRequestDto httpRequestDto = ShortHttpGetRequestDto.builder()
                    .fullPath(request.getPathInfo())
                    .path("/" + request.getPathInfo().split("/")[1])
                    .method(request.getMethod())
                    .pathVariableName(request.getPathInfo().split("/")[2])
                    .pathVariableValue(request.getPathInfo().split("/")[3])
                    .fullPathLength(request.getPathInfo().split("/").length)
                    .build();
            return httpRequestDto;
        } else {
            log.error("Not valid path {}", request.getPathInfo());
            throw new IllegalArgumentException("Invalid path " + request.getPathInfo());
        }
    }

    public static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> params =
                request.getParameterMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()[0]
                        ));
        return params;
    }

    public static ShortHttpPostRequestDto createPostHttpDto(HttpServletRequest request) {
        return ShortHttpPostRequestDto.builder()
                .fullPath(request.getPathInfo())
                .method(request.getMethod())
                .body(getParameters(request))
                .build();
    }
}



