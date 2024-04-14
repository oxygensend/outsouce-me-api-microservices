-- liquibase formatted sql

ALTER TABLE user
    ADD CONSTRAINT unique_slug UNIQUE (slug);
