CREATE TABLE IF NOT EXISTS players
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    sex VARCHAR(10) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    title_id BIGINT REFERENCES titles(id),
    elo_rating INT NOT NULL,
    insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    edit_date TIMESTAMP DEFAULT NULL
);