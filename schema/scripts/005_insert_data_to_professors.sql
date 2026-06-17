-- liquibase formatted sql

-- changeset terentem:5-insert-data-to-professors
COPY professors (name, email, department_id)
    FROM '/var/lib/postgresql/csv_data/professors.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE professors CASCADE;