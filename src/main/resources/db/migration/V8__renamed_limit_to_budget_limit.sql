ALTER TABLE budget_category
    ADD budget_limit DECIMAL;

ALTER TABLE budget_category
    DROP COLUMN "limit";