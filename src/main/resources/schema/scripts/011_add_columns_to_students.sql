-- liquibase formatted sql

-- changeset terentem:11-add-columns-to-students

ALTER TABLE students
    ADD COLUMN gender VARCHAR(8),
    ADD COLUMN academic_interests INT[];

-- rollback ALTER TABLE student DROP COLUMN gender, DROP COLUMN academic_interests