SET CLIENT_ENCODING="UTF8";
BEGIN;

CREATE TABLE IF NOT EXISTS projekt(
	projekt_id SERIAL,
	nazwa VARCHAR(50) NOT NULL,
	opis VARCHAR(1000),
	dataczas_utworzenia TIMESTAMP DEFAULT now(),
	dataczas_modyfikacji TIMESTAMP,
	data_oddania DATE,
	CONSTRAINT projekt_pk PRIMARY KEY (projekt_id)
);

CREATE TABLE IF NOT EXISTS zadanie(
	zadanie_id SERIAL,
	nazwa VARCHAR(50) NOT NULL,
	opis VARCHAR(1000),
	kolejnosc INTEGER,
	dataczas_utworzenia TIMESTAMP DEFAULT now(),
	dataczas_modyfikacji TIMESTAMP,
	projekt_id INTEGER NOT NULL,
	CONSTRAINT zadanie_pk PRIMARY KEY (zadanie_id)
);

ALTER TABLE zadanie DROP CONSTRAINT IF EXISTS zadanie_projekt_fk;
ALTER TABLE zadanie ADD CONSTRAINT zadanie_projekt_fk FOREIGN KEY (projekt_id) REFERENCES projekt (projekt_id); -- ON DELETE CASCADE;

ALTER TABLE zadanie DROP CONSTRAINT IF EXISTS unique_kolejnosc;
ALTER TABLE zadanie ADD CONSTRAINT unique_kolejnosc UNIQUE (kolejnosc, projekt_id);

CREATE INDEX IF NOT EXISTS projekt_nazwa_idx ON projekt(nazwa);

CREATE INDEX IF NOT EXISTS zadanie_nazwa_idx ON zadanie(nazwa);

COMMIT;