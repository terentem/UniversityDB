-- liquibase formatted sql

-- changeset terentem:1-create-departments
CREATE TABLE departments (
                             id BIGSERIAL,
                             name VARCHAR(255) NOT NULL,
                             building VARCHAR(255) NOT NULL,
                             CONSTRAINT pk_departments PRIMARY KEY (id),
                             CONSTRAINT uq_departments_name UNIQUE (name)
);
-- rollback DROP TABLE departments;

-- changeset terentem:2-create-term-types
CREATE TABLE term_types (
                            id BIGSERIAL,
                            title VARCHAR(50) NOT NULL,
                            CONSTRAINT pk_term_types PRIMARY KEY (id)
);
-- rollback DROP TABLE term_types;

-- changeset terentem:3-create-professors
CREATE TABLE professors (
                            id BIGSERIAL,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            department_id BIGINT NOT NULL,
                            CONSTRAINT pk_professors PRIMARY KEY (id),
                            CONSTRAINT uq_professors_email UNIQUE (email),
                            CONSTRAINT fk_professors_departments FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);
-- rollback DROP TABLE professors;

-- changeset terentem:4-create-students
CREATE TABLE students (
                          id BIGSERIAL,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          enrollment_date DATE NOT NULL,
                          CONSTRAINT pk_students PRIMARY KEY (id),
                          CONSTRAINT uq_students_email UNIQUE (email)
);
-- rollback DROP TABLE students;

-- changeset terentem:5-create-courses
CREATE TABLE courses (
                         id BIGSERIAL,
                         course_code VARCHAR(50) NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         number_of_credits INT NOT NULL,
                         department_id BIGINT NOT NULL,
                         CONSTRAINT pk_courses PRIMARY KEY (id),
                         CONSTRAINT uq_courses_course_code UNIQUE (course_code),
                         CONSTRAINT fk_courses_departments FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);
-- rollback DROP TABLE courses;

-- changeset terentem:6-create-courses-prerequisites
CREATE TABLE courses_prerequisites (
                                       course_id BIGINT NOT NULL,
                                       prerequisite_course_id BIGINT NOT NULL,
                                       CONSTRAINT pk_courses_prerequisites PRIMARY KEY (course_id, prerequisite_course_id),
                                       CONSTRAINT fk_prerequisites_main_course FOREIGN KEY (course_id) REFERENCES courses(id),
                                       CONSTRAINT fk_prerequisites_parent_course FOREIGN KEY (prerequisite_course_id) REFERENCES courses(id) ON DELETE CASCADE
);
-- rollback DROP TABLE courses_prerequisites;

-- changeset terentem:7-create-offerings
CREATE TABLE offerings (
                           id BIGSERIAL,
                           course_id BIGINT NOT NULL,
                           term_id BIGINT NOT NULL,
                           professor_id BIGINT NOT NULL,
                           CONSTRAINT pk_offerings PRIMARY KEY (id),
                           CONSTRAINT fk_offerings_courses FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE RESTRICT,
                           CONSTRAINT fk_offerings_terms FOREIGN KEY (term_id) REFERENCES term_types(id) ON DELETE RESTRICT,
                           CONSTRAINT fk_offerings_professors FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE RESTRICT
);
-- rollback DROP TABLE offerings;

-- changeset terentem:8-create-offerings-students
CREATE TABLE offerings_students (
                                    student_id BIGINT NOT NULL,
                                    offering_id BIGINT NOT NULL,
                                    grade VARCHAR(10),
                                    CONSTRAINT pk_offerings_students PRIMARY KEY (student_id, offering_id),
                                    CONSTRAINT fk_offerings_students_student FOREIGN KEY (student_id) REFERENCES students(id),
                                    CONSTRAINT fk_offerings_students_offering FOREIGN KEY (offering_id) REFERENCES offerings(id)
);
-- rollback DROP TABLE offerings_students;