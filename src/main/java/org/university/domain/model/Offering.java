package org.university.domain.model;

import java.util.List;

public record Offering(
        int id,
        int courseId,
        int termId,
        int professorId
) {
}
