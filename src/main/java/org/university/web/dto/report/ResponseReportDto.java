package org.university.web.dto.report;


public record ResponseReportDto(
        Long offeringId,
        Long numberOfStudents
) {
    //По мірі необхідності буду додавати конструктори для репортів,
    // яким потрібний інший набір параметрів через  @ReportConstructor(reportId = "some-report-id")
}
