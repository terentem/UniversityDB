package org.university.web.dto;

public record ShortHttpGetRequestDto(
        String fullPath,
        String path,
        String method,
        String pathVariableName,
        String pathVariableValue,
        int fullPathLength
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fullPath;
        private String path;
        private String method;
        private String pathVariableName;
        private String pathVariableValue;
        private int fullPathLength;

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder fullPath(String fullPath) {
            this.fullPath = fullPath;
            return this;
        }

        public Builder pathVariableName(String pathVariableName) {
            this.pathVariableName = pathVariableName;
            return this;
        }

        public Builder pathVariableValue(String pathVariableValue) {
            this.pathVariableValue = pathVariableValue;
            return this;
        }

        public Builder fullPathLength(int fullPathLength) {
            this.fullPathLength = fullPathLength;
            return this;
        }

        public ShortHttpGetRequestDto build() {
            return new ShortHttpGetRequestDto(fullPath, path, method, pathVariableName,pathVariableValue,fullPathLength );
        }
    }
}