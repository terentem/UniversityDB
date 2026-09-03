package org.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Enrollment;
import org.university.domain.model.Offering;
import org.university.domain.model.Student;
import org.university.repository.EnrollmentRepository;
import org.university.web.dto.enrollment.RequestEnrollmentDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository repository;
    private final StudentService studentService;
    private final OfferingService offeringService;

    public EnrollmentService(EnrollmentRepository repository, StudentService studentService, OfferingService offeringService) {
        this.repository = repository;
        this.studentService = studentService;
        this.offeringService = offeringService;
    }

    public List<Enrollment> create(RequestEnrollmentDto enrollmentDto) throws SQLException {
        return repository.create(enrollmentDto).orElse(Collections.emptyList());
    }

    public List<Enrollment> createAll(RequestEnrollmentDto enrollmentDto) throws SQLException {
        List<Offering> offering = offeringService.get(enrollmentDto.offeringId());
        int courseInterestMatch = offering.getFirst().courseId();
        log.debug("Offering data= {}, courseInterestMatch={}", offering, courseInterestMatch);
        List<Student> students = studentService.getByInterestId(courseInterestMatch);
        log.debug("student.length= {} records", students.size());
        for (Student student : students) {
            enrollmentDto = new RequestEnrollmentDto(student.id(), offering.getFirst().id(), null);
            repository.create(enrollmentDto).orElse(Collections.emptyList()).getFirst();
        }
        return Collections.emptyList();
    }

    public List<Enrollment> update(RequestEnrollmentDto enrollmentDto, Integer studentId, Integer offeringId) throws SQLException {
        return repository.update(enrollmentDto, studentId, offeringId).orElse(Collections.emptyList());
    }

    public List<Enrollment> delete(Integer studentId, Integer offeringId) throws SQLException {
        return repository.deleteById(studentId, offeringId).orElse(Collections.emptyList());
    }
}



