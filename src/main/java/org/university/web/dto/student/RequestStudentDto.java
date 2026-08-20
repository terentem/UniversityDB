package org.university.web.dto.student;

import java.time.LocalDate;

public record RequestStudentDto(
                                Integer id,
                                String name,
                                String email,
                                LocalDate enrollmentDate,
                                LocalDate birthday,
                                String fundingSource,
                                Long admissionScore,
                                Boolean specialNeeds,
                                String address,
                                String phoneNumber,
                                String gender,
                                int[] academicInterests
) {
}

