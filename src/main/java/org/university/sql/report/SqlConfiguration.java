package org.university.sql.report;

import java.util.Map;

public enum SqlConfiguration {

    STUDENTS_BY_OFFER_ID("students-by-offering-id", new String[]{}) {
        @Override
        public String getSql() {
            return """
                    SELECT offering_id, count(student_id) as number_of_students
                    FROM offerings_students
                    GROUP BY offering_id
                    """;
        }
    };

    private final String reportId;
    private final String[] paramsNames;

    SqlConfiguration(String reportId, String[] paramsNames) {
        this.reportId = reportId;
        this.paramsNames = paramsNames;
    }

    public abstract String getSql();

    public static SqlConfiguration getById(String reportId) {
        for (SqlConfiguration r : values()) {
            if (r.reportId.equalsIgnoreCase(reportId)) return r;
        }
        throw new IllegalArgumentException("Unknown report name: " + reportId);
    }

    public Object[] extractParams(Map<String, String[]> queryParams) {
        Object[] sqlValues = new Object[this.paramsNames.length];

        for (int i = 0; i < this.paramsNames.length; i++) {
            String currentParamName = this.paramsNames[i];
            String[] paramValues = queryParams.get(currentParamName);

            // Безопасная проверка: проверяем сам массив, его длину и что первый элемент не null/не пустой
            if (paramValues == null || paramValues.length == 0 || paramValues[0] == null || paramValues[0].isBlank()) {
                throw new IllegalArgumentException("Missing required query parameter: " + currentParamName);
            }

            String rawValue = paramValues[0];

            if (rawValue.matches("-?\\d+")) {
                sqlValues[i] = Integer.parseInt(rawValue);
            } else {
                sqlValues[i] = rawValue;
            }
        }
        return sqlValues;
    }
}

