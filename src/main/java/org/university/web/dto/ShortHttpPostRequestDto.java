package org.university.web.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record ShortHttpPostRequestDto(
        String fullPath,
        String method,
        Map<String, String> body
) {
    public Optional<String> getParameter(String name) {
        return Optional.ofNullable(body.get(name));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fullPath;
        private String method;
        private Map<String, String> body;

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder fullPath(String fullPath) {
            this.fullPath = fullPath;
            return this;
        }

        public Builder body(Map<String, String> body) {
            this.body = body != null ? new HashMap<>(body) : new HashMap<>();
            return this;
        }

        public ShortHttpPostRequestDto build() {
            return new ShortHttpPostRequestDto(fullPath, method, body);
        }
    }
}

