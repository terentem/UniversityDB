package org.university.web.dto.student;

import org.university.domain.model.Student;

import java.time.LocalDate;

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
        String phoneNumber,
        String gender,
        int[] academic_interests) {

    public static ResponseStudentDto toPostPutDeleteDto(Student student) {
        return new ResponseStudentDto(
                student.id(),
                student.name(),
                student.email(),
                student.enrollmentDate(),
                student.birthday(),
                student.fundingSource(),
                student.admissionScore(),
                student.specialNeeds(),
                student.address(),
                student.phoneNumber(),
                student.gender(),
                student.academicInterests()
        );
    }

    public static ResponseStudentDto toGteDto(Student student) {
        return new ResponseStudentDto(
                student.id(),
                student.name(),
                student.email(),
                student.enrollmentDate(),
                student.birthday(),
                student.fundingSource(),
                student.admissionScore(),
                student.specialNeeds(),
                student.address(),
                student.phoneNumber(),
                student.gender(),
                student.academicInterests()
        );
    }
}
