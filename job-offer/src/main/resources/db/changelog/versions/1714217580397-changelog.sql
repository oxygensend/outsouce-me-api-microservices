-- liquibase formatted sql

-- changeset oxygensend:1714217580397-1
ALTER TABLE salary_range MODIFY currency VARCHAR (3);

-- changeset oxygensend:1714217580397-2
ALTER TABLE user
    ADD account_type SMALLINT NULL;

