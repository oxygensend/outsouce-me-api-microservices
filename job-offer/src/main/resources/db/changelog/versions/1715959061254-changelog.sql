-- liquibase formatted sql

-- changeset oxygensend:1715959061254-3
ALTER TABLE user
    ADD latitude DOUBLE NULL, ADD longitude DOUBLE NULL;
