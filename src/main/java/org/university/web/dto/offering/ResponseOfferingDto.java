package org.university.web.dto.offering;

import org.university.domain.model.Offering;
import org.university.domain.model.Student;

import java.time.LocalDate;

public record ResponseOfferingDto(
        int id,
        int courseId,
        int termId,
        int professorId
        ) {

    public static ResponseOfferingDto toPostPutDeleteDto(Offering offering) {
        return new ResponseOfferingDto(
                offering.id(),
                offering.courseId(),
                offering.termId(),
                offering.professorId()
                       );
    }

    public static ResponseOfferingDto toRead(Offering offering) {
        return new ResponseOfferingDto(
                offering.id(),
                offering.courseId(),
                offering.termId(),
                offering.professorId()
        );
    }

}
