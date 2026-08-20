package org.university.web.dto.offering;

import java.time.LocalDate;

public record RequestOfferingDto(
                                int id,
                                int courseId,
                                int termId,
                                int professorId

) {
}

