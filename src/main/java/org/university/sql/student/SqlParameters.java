package org.university.sql.student;

import java.time.LocalDate;
import java.util.Date;

public record SqlParameters(Integer id,
                            String name,
                            String email,
                            LocalDate enrollmentDate,
                            LocalDate birthday,
                            String fundingSource,
                            Long admissionScore,
                            Boolean specialNeeds,
                            String address,
                            String phoneNumber) {

    public SqlParameters(
                         String name,
                         String email,
                         LocalDate enrollmentDate,
                         LocalDate birthday,
                         String fundingSource,
                         Long admissionScore,
                         Boolean specialNeeds,
                         String address,
                         String phoneNumber){
        this(null,name,email,enrollmentDate,birthday,fundingSource,admissionScore,specialNeeds,address,phoneNumber);
    }
}
