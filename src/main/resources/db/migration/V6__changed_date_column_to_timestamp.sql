ALTER TABLE expense
    ADD expense_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE expense
    DROP COLUMN date;