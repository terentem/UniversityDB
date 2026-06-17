-- liquibase formatted sql

-- changeset terentem:6-add-unique-to-offerings

ALTER TABLE offerings
    ADD CONSTRAINT uq_offerings_course_term_professor
        UNIQUE (course_id, term_id, professor_id);

-- rollback ALTER TABLE offerings DROP CONSTRAINT uq_offerings_course_term_professor;