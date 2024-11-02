CREATE TABLE IF NOT EXISTS players
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    sex VARCHAR(6) CHECK (sex IN ('MALE', 'FEMALE')),
    nationality VARCHAR(255),
    title_id BIGINT REFERENCES titles(id),
    elo_rating INT,
    insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    edit_date TIMESTAMP,
    UNIQUE (first_name, last_name, birth_date)
);