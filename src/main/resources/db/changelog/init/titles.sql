CREATE TABLE IF NOT EXISTS titles
(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
	abbreviation VARCHAR (3) NOT NULL UNIQUE,
	elo_threshold integer NOT NULL,
	requires_norm boolean NOT NULL,
	only_female boolean NOT NULL,
	insert_date timestamp DEFAULT CURRENT_TIMESTAMP,
	edit_date timestamp DEFAULT NULL
);

INSERT INTO titles (name, abbreviation, elo_threshold, requires_norm, only_female, insert_date)
VALUES
('National Master', 'NM', 2200, false, false, NOW()),
('Woman Candidate Master', 'WCM', 2000, false, true, NOW()),
('Woman FIDE Master', 'WFM', 2100, false, true, NOW()),
('Woman International Master', 'WIM', 2200, true, true, NOW()),
('Woman Grandmaster', 'WGM', 2300, true, true, NOW()),
('Candidate Master', 'CM', 2200, false, false, NOW()),
('FIDE Master', 'FM', 2300, false, false, NOW()),
('International Master', 'IM', 2400, true, false, NOW()),
('Grandmaster', 'GM', 2500, true, false, NOW());