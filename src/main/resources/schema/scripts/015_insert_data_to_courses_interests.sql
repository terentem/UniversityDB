-- liquibase formatted sql

-- changeset terentem:15-insert-data-to-courses_interests
COPY courses_interests (course_id,interest_id)
    FROM '/var/lib/postgresql/csv_data/corses_interests_map.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE courses_interests CASCADE;