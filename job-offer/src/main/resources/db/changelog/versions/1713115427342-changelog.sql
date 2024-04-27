-- liquibase formatted sql

-- changeset oxygensend:1713115311111-1
ALTER TABLE user ADD COLUMN thumbnail VARCHAR(255) NULL;
