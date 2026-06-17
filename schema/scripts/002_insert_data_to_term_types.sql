-- liquibase formatted sql

-- changeset terentem:2-insert-data-to-term-types
COPY term_types (title)
    FROM '/var/lib/postgresql/csv_data/term_types.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE term_types CASCADE;

-- changeset terentem:3-insert-data-to-departments
COPY departments (name, building)
    FROM '/var/lib/postgresql/csv_data/departments.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE departments CASCADE;