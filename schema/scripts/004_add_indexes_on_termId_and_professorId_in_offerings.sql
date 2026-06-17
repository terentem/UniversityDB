-- liquibase formatted sql

-- changeset terentem:4-add-offerings-indexes
CREATE INDEX idx_offerings_term_id ON offerings(term_id);

CREATE INDEX idx_offerings_professor_id ON offerings(professor_id);

-- rollback DROP INDEX idx_offerings_term_id;
-- rollback DROP INDEX idx_offerings_professor_id;