package org.example.web;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.stream.Collectors;

public record RequestMapper() {

    public static Request mapToRequest(HttpServletRequest request) {

        Map<String, String> params =
                request.getParameterMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()[0]
                        ));

        return new Request(
                request.getPathInfo(),
                request.getMethod(),
                params
        );
    }
}
