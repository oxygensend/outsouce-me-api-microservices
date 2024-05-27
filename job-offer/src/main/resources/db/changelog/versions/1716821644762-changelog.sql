-- liquibase formatted sql

-- changeset oxygensend:1716821644762-5
ALTER TABLE user ADD opinions_count INT  NOT NULL, ADD opinions_rate DOUBLE  NOT NULL;


-- changeset oxygensend:1716821644762-1
ALTER TABLE address DROP COLUMN lat;
ALTER TABLE address DROP COLUMN lon;

-- changeset oxygensend:1716821644762-2
ALTER TABLE address
    ADD lat DOUBLE NULL;

-- changeset oxygensend:1716821644762-4
ALTER TABLE address
    ADD lon DOUBLE NULL;

