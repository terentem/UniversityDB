package org.university.web.dto.offering;

public record RequestOfferingDto(
                                int id,
                                int courseId,
                                int termId,
                                int professorId

) {
}

