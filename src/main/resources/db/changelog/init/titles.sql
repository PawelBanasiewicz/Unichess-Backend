CREATE TABLE IF NOT EXISTS titles
(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
	abbreviation VARCHAR (3) NOT NULL UNIQUE,
	elo_threshold integer NOT NULL,
	requires_norm boolean NOT NULL,
	only_female boolean NOT NULL,
	introduction_year integer NOT NULL,
	insert_date timestamp DEFAULT CURRENT_TIMESTAMP,
	edit_date timestamp DEFAULT NULL
);

INSERT INTO titles (name, abbreviation, elo_threshold, requires_norm, only_female, introduction_year)
VALUES
('National Master', 'NM', 2200, false, false, 2002),
('Woman Candidate Master', 'WCM', 2000, false, true, 2002),
('Woman FIDE Master', 'WFM', 2100, false, true, 1978),
('Woman International Master', 'WIM', 2200, true, true, 1950),
('Woman Grandmaster', 'WGM', 2300, true, true, 1976),
('Candidate Master', 'CM', 2200, false, false, 2002),
('FIDE Master', 'FM', 2300, false, false, 1978),
('International Master', 'IM', 2400, true, false, 1950),
('Grandmaster', 'GM', 2500, true, false, 1950);