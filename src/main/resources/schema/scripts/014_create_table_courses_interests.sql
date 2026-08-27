-- liquibase formatted sql

-- changeset terentem:14-create-courses-interests
CREATE TABLE courses_interests
(
    course_id   BIGINT NOT NULL,
    interest_id BIGINT NOT NULL
);
-- rollback DROP TABLE courses_interests;