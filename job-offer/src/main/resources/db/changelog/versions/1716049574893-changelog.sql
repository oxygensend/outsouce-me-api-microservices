-- liquibase formatted sql

-- changeset oxygensend:1716049574893-5
ALTER TABLE user
    ADD experience INT NULL, ADD technologies VARCHAR(255) NULL;

-- changeset oxygensend:1716049574893-1
ALTER TABLE address DROP COLUMN lat;
ALTER TABLE address DROP COLUMN lon;

-- changeset oxygensend:1716049574893-2
ALTER TABLE address
    ADD lat DOUBLE NULL;

-- changeset oxygensend:1716049574893-4
ALTER TABLE address
    ADD lon DOUBLE NULL;

