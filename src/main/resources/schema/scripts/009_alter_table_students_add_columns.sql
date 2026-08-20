-- liquibase formatted sql

-- changeset terentem:9-add-columns-to-students

ALTER TABLE students
    ADD COLUMN birthday DATE,
    ADD COLUMN funding_source VARCHAR(30), -- 'STATE_FUNDED', 'SELF_FUNDED', 'STUDENT_LOAN'
    ADD COLUMN admission_score NUMERIC(5,2),
    ADD COLUMN has_special_needs BOOLEAN,
    ADD COLUMN address VARCHAR(255),
    ADD COLUMN phone_number VARCHAR(20);

-- rollback ALTER TABLE student DROP COLUMN birthday, DROP COLUMN funding_source, DROP COLUMN admission_score, DROP COLUMN has_special_needs, DROP COLUMN address, DROP COLUMN phone_number;