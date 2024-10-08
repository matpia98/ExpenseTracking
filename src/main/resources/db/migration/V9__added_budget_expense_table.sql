ALTER TABLE budget_category
    DROP CONSTRAINT fk_budgetcategory_on_budget;

CREATE TABLE budget_expense
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title     VARCHAR(255),
    amount    DECIMAL(10, 2),
    date      TIMESTAMP WITHOUT TIME ZONE,
    budget_id BIGINT,
    CONSTRAINT pk_budgetexpense PRIMARY KEY (id)
);

ALTER TABLE budget
    ADD remaining DECIMAL(10, 2);

ALTER TABLE budget
    ADD spent DECIMAL(10, 2);

ALTER TABLE budget_expense
    ADD CONSTRAINT FK_BUDGETEXPENSE_ON_BUDGET FOREIGN KEY (budget_id) REFERENCES budget (id);

ALTER TABLE budget_category
    DROP COLUMN budget_id;