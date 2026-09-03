package org.university.domain.model;

public record Report(String title, String sqlScript, Object[] sqlParams) {
}
