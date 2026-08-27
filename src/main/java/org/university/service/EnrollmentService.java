package org.university.service;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.domain.model.Enrollment;
import org.university.domain.model.Offering;
import org.university.domain.model.OfferingStudent;
import org.university.domain.model.Student;
import org.university.repository.EnrollmentRepository;
import org.university.repository.OfferingRepository;
import org.university.repository.StudentRepository;
import org.university.web.dto.enrollment.RequestEnrollmentDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository repository;

    public EnrollmentService(EnrollmentRepository repository) {
        this.repository = repository;
    }

  /*  public List<Offering> get(Integer id) throws SQLException {
        //ВИзначаємо метод для пошуку
        if (id == null) {
            log.info("id = params={}", id);
            return repository.findAll().orElse(Collections.emptyList());
        } else {
            log.info("id = params={}", id);
            return repository.findById(id).orElse(Collections.emptyList());
        }
    }*/

    public List<Enrollment> create(RequestEnrollmentDto enrollmentDto) throws SQLException {
        return repository.create(enrollmentDto).orElse(Collections.emptyList());
    }

    public List<Enrollment> batch(RequestEnrollmentDto enrollmentDto) throws SQLException {
        //Get data from offerings table by offeringId
        OfferingRepository offeringRepository = new OfferingRepository();
        List<Offering> offering = offeringRepository.findById(enrollmentDto.offeringId()).orElse(Collections.emptyList());
        //Get match of interest for courseId
        int courseInterestMatch = offering.getFirst().courseId();
        log.info("Offering data= {}, courseInterestMatch={}", offering, courseInterestMatch);
        //Get students by interestId
        StudentRepository studentRepository=new StudentRepository();
        List<Student> students=studentRepository.findSTudentsByInterestId(courseInterestMatch).orElse(Collections.emptyList());
        log.info("student.length= {} records",students.size());
        //insert butch
        OfferingStudent offeringStudent=new OfferingStudent(offering.getFirst(),students,courseInterestMatch);

        return repository.createbyBatch(offeringStudent).orElse(Collections.emptyList());
    }

    public List<Enrollment> update(RequestEnrollmentDto enrollmentDto, Integer pathVariable) throws SQLException {
        return repository.update(enrollmentDto, pathVariable).orElse(Collections.emptyList());
    }

    public List<Enrollment> delete(Integer studentId, Integer offeringId) throws SQLException {
        return repository.deleteById(studentId, offeringId).orElse(Collections.emptyList());
    }
}



