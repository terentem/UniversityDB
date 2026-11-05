package org.university.sql.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SqlScripts {

    STUDENTS_BY_OFFER_ID("students-by-offering-id") {
        @Override
        public String getSql() {
            return """
                    SELECT offering_id, count(student_id) as number_of_students
                    FROM offerings_students
                    GROUP BY offering_id
                    """;
        }
    },
    STUDENTS_GROUPED_BY_OFFERING_ID_WITH_FILTER_GRADE("students-grouped-by-offeringId-with-filter-grade") {
        @Override
        public String getSql() {
            return """
                    SELECT offering_id, count(student_id) as number_of_students
                    FROM offerings_students
                    WHERE grade=?
                    GROUP BY offering_id
                    """;
        }
    };

    private final String reportId;
    private static final Logger log = LoggerFactory.getLogger(SqlScripts.class);

    SqlScripts(String reportId) {
        this.reportId = reportId;
    }

    public abstract String getSql();

    public static SqlScripts getById(String reportId) {
        for (SqlScripts r : values()) {
            if (r.reportId.equalsIgnoreCase(reportId)) return r;
        }
        throw new IllegalArgumentException("Unknown report name: " + reportId);
    }

}

