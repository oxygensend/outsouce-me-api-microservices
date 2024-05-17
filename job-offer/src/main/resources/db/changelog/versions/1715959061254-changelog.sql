-- liquibase formatted sql

-- changeset oxygensend:1715959061254-3
ALTER TABLE user
    ADD latitude DOUBLE NULL, ADD longitude DOUBLE NULL;

-- changeset oxygensend:1715959061254-1
ALTER TABLE user MODIFY opinions_count INT NOT NULL;

-- changeset oxygensend:1715959061254-2
ALTER TABLE user MODIFY opinions_rate DOUBLE NOT NULL;

