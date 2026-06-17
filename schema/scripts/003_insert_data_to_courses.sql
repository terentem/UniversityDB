-- liquibase formatted sql

-- changeset terentem:3-insert-data-to-courses
COPY courses (course_code,title,number_of_credits,department_id)
    FROM '/var/lib/postgresql/csv_data/courses.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE courses CASCADE;