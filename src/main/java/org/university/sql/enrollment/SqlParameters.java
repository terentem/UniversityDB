package org.university.sql.enrollment;

import org.university.domain.model.OfferingStudent;
import org.university.domain.model.Student;
import org.university.web.dto.enrollment.RequestEnrollmentDto;

import java.util.List;

public record SqlParameters(Integer interestId,
                            Integer studentId,
                            Integer offeringId,
                            String grade,
                            List<Student> students
) {

    public static SqlParameters forInterestId(Integer id) {
        return new SqlParameters(
                id,
                null,
                null,
                null,
                null
        );
    }

    public static SqlParameters forCompoundId(Integer studentId, Integer offeringId) {
        return new SqlParameters(
                null,
               studentId,
                offeringId,
                null,
                null
        );
    }


    public static SqlParameters forCreate(RequestEnrollmentDto dto) {
        return new SqlParameters(
                null,
                dto.studentId(),
                dto.offeringId(),
                dto.grade(),
                null
        );
    }

    public static SqlParameters forCreateByButch(OfferingStudent dto) {
        return new SqlParameters(
                null,
                null,
                dto.offering().id(),
                null,
                dto.students()
        );
    }

    public static SqlParameters forUpdate(
            Integer studentId,
            Integer offeringId,
            RequestEnrollmentDto dto
    ) {
        return new SqlParameters(
                null,
                studentId,
                offeringId,
                dto.grade(),
                null
        );
    }

}
