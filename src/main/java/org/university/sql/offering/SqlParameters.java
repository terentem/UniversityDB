package org.university.sql.offering;

import org.university.web.dto.offering.RequestOfferingDto;

public record SqlParameters(Integer id,
                            Integer courseId,
                            Integer termId,
                            Integer professorId
) {

    public static SqlParameters forId(Integer id) {
        return new SqlParameters(
                id,
                null,
                null,
                null
        );
    }

    public static SqlParameters forAll() {
        return new SqlParameters(
                null,
                null,
                null,
                null
        );
    }

    public static SqlParameters forCreate(RequestOfferingDto dto) {
        return new SqlParameters(
                null,
                dto.courseId(),
                dto.termId(),
                dto.professorId()
        );
    }

    public static SqlParameters forUpdate(
            Integer id,
            RequestOfferingDto dto
    ) {
        return new SqlParameters(
                id,
                dto.courseId(),
                dto.termId(),
                dto.professorId()
        );
    }
}
