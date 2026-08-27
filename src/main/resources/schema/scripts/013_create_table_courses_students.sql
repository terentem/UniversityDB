-- liquibase formatted sql

-- changeset terentem:13-create-courses-students
CREATE TABLE courses_students
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    term_id    BIGINT NOT NULL,
    CONSTRAINT pk_courses_students_terms PRIMARY KEY (student_id, course_id, term_id),
    CONSTRAINT fk_courses_students_terms_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_courses_students_terms_course FOREIGN KEY (course_id) REFERENCES courses (id),
    CONSTRAINT fk_courses_students_terms_term FOREIGN KEY (term_id) REFERENCES term_types(id)
);
-- rollback DROP TABLE courses_students;