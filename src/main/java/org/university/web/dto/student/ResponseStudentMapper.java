package org.university.web.dto.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Student;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResponseStudentMapper {
    private static final Logger log = LoggerFactory.getLogger(ResponseStudentMapper.class);

    public static List<ResponseStudentDto> createGetHttpResponseDto(Optional<List<Student>> optionalResult) {
        List<Student> studentList = optionalResult.orElse(Collections.emptyList());
        List<ResponseStudentDto> responseDto = studentList.stream()
                .map(s -> new ResponseStudentDto(s.id(), s.name(), s.email(), s.enrollmentDate(), s.birthday(), s.fundingSource(), s.admissionScore(), s.specialNeeds(), s.address(), s.phoneNumber()))
                .toList();
        return responseDto;
    }

    public static List<ResponseStudentDto> createPostPutDeleteHttpResponseDto(Optional<List<Student>> optionalResult) {
        List<Student> studentList = optionalResult.orElse(Collections.emptyList());
        List<ResponseStudentDto> responseDto = studentList.stream()
                .map(s -> new ResponseStudentDto(s.id(), s.name(), s.email(), s.enrollmentDate(), s.birthday(), s.fundingSource(),s.admissionScore(), s.specialNeeds(), s.address(), s.phoneNumber()))
                .toList();
        return responseDto;
    }
}



