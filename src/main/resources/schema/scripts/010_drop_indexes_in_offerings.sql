-- liquibase formatted sql

-- changeset terentem:10-drop-indexes-in-offerings

DROP INDEX IF EXISTS idx_offerings_term_id;
DROP INDEX IF EXISTS idx_offerings_professor_id;

-- rollback DROP INDEX idx_offerings_term_id;
-- rollback DROP INDEX idx_offerings_professor_id;