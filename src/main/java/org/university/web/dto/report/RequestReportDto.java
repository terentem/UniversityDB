package org.university.web.dto.report;

public record RequestReportDto(
        Integer offeringId,
        Integer professorId,
        String grade
) {
    // Конструктор для звіту по кількості студентів в кожному offeringId
    @ReportConstructor(reportId = "students-by-offering-id")
    public RequestReportDto() {
        this(null, null, null);
    }

    // Конструктор для звіту по кількості студентів в кожному offeringId з фільтром grade
    @ReportConstructor(reportId = "students-grouped-by-offeringId-with-filter-grade")
    public RequestReportDto(String grade) {
        this(null, null,grade);
    }
}

