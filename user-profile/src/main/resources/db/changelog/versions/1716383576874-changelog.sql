-- liquibase formatted sql

-- changeset oxygensend:1716383576874-3
CREATE INDEX externalId_idx ON user (external_id);

