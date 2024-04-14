-- liquibase formatted sql

-- changeset oxygensend:1713115311111-1
ALTER TABLE user ADD COLUMN active_job_position VARCHAR(255) NULL;