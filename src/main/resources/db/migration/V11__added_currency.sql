ALTER TABLE expense ADD COLUMN currency VARCHAR(3) NOT NULL DEFAULT 'PLN';
UPDATE expense SET currency = 'PLN' WHERE currency IS NULL;