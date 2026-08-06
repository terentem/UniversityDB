package org.university.web.dto.student;

import java.time.LocalDate;
import java.util.Date;

public record ResponseStudentDto(
                                 Integer id,
                                 String name,
                                 String email,
                                 LocalDate enrollmentDate,
                                 LocalDate birthday,
                                 String fundingSource,
                                 Long admissionScore,
                                 Boolean specialNeeds,
                                 String address,
                                 String phoneNumber) {
}
