package org.university.web.dto.student;

import java.time.LocalDate;
import java.util.Date;

public record RequestStudentDto(//for parameters in PUT/ POST http request
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
    public RequestStudentDto(String name, String email, LocalDate enrollmentDate, LocalDate birthday, String fundingSource,Long admissionScore, Boolean specialNeeds, String address, String phoneNumber) {
        this(null, name, email, enrollmentDate, birthday, fundingSource,admissionScore, specialNeeds, address, phoneNumber);
    }
}
