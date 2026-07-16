package org.example.web;

import java.util.Map;
import java.util.Optional;

public record Request(
        String path,
        String method,
        Map<String, String> parameters
) {
  public Optional<String> getParameter(RequestParameter parameter) {
    return Optional.ofNullable(parameters.get(parameter.value()));
  }
  }
