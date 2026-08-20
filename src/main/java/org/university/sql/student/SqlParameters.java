package org.university.sql.student;

import org.university.web.dto.student.RequestStudentDto;

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
                            String phoneNumber,
                            String gender,
                            int[] academicInterests) {

    public static SqlParameters forId(Integer id) {
        return new SqlParameters(
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
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
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static SqlParameters forCreate(RequestStudentDto dto) {
        return new SqlParameters(
                null,
                dto.name(),
                dto.email(),
                dto.enrollmentDate(),
                dto.birthday(),
                dto.fundingSource(),
                dto.admissionScore(),
                dto.specialNeeds(),
                dto.address(),
                dto.phoneNumber(),
                dto.gender(),
                dto.academicInterests()
        );
    }

    public static SqlParameters forUpdate(
            Integer id,
            RequestStudentDto dto
    ) {
        return new SqlParameters(
                id,
                dto.name(),
                dto.email(),
                dto.enrollmentDate(),
                dto.birthday(),
                dto.fundingSource(),
                dto.admissionScore(),
                dto.specialNeeds(),
                dto.address(),
                dto.phoneNumber(),
                dto.gender(),
                dto.academicInterests()
        );
    }
}
