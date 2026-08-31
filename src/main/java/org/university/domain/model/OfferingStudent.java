package org.university.domain.model;

import java.util.List;

public record OfferingStudent(
        Offering offering,
        List<Student> students,
        int courseInterestMatch
) {
}
