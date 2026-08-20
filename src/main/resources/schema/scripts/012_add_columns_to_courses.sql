-- liquibase formatted sql

-- changeset terentem:12-add-columns-to-courses

ALTER TABLE courses
    ADD COLUMN course_category VARCHAR(30)


-- rollback ALTER TABLE courses DROP COLUMN course_category