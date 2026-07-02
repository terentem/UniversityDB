-- liquibase formatted sql

-- changeset terentem:8-insert-data-to-offerings
COPY offerings (course_id,term_id,professor_id)
    FROM '/var/lib/postgresql/csv_data/offerings.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE offerings CASCADE;