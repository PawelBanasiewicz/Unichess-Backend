CREATE TABLE IF NOT EXISTS titles
(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
	abbreviation VARCHAR (3) NOT NULL UNIQUE,
	elo_threshold integer NOT NULL,
	requires_norm boolean NOT NULL,
	only_female boolean NOT NULL,
	insert_date timestamp DEFAULT CURRENT_TIMESTAMP,
	edit_date timestamp DEFAULT NULL
);

INSERT INTO titles (name, abbreviation, elo_threshold, requires_norm, only_female)
VALUES
('National Master', 'NM', 2200, false, false),
('Woman Candidate Master', 'WCM', 2000, false, true),
('Woman FIDE Master', 'WFM', 2100, false, true),
('Woman International Master', 'WIM', 2200, true, true),
('Woman Grandmaster', 'WGM', 2300, true, true),
('Candidate Master', 'CM', 2200, false, false),
('FIDE Master', 'FM', 2300, false, false),
('International Master', 'IM', 2400, true, false),
('Grandmaster', 'GM', 2500, true, false);