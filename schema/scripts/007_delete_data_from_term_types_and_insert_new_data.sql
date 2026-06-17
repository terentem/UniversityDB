-- liquibase formatted sql

-- changeset terentem:7-update-term-types-data

TRUNCATE TABLE term_types CASCADE;
COPY term_types (title)
    FROM '/var/lib/postgresql/csv_data/term_types.csv'
    DELIMITER ',' CSV HEADER;
-- rollback TRUNCATE TABLE term_types CASCADE;
