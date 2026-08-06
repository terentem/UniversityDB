package org.university.domain.model;

import java.time.LocalDate;
import java.util.Date;

public record Student(
        Integer id,
        String name,
        String email,
        LocalDate enrollmentDate,
        LocalDate birthday,
        String fundingSource,
        Long admissionScore,
        Boolean specialNeeds,
        String address,
        String phoneNumber
        ) {

}
