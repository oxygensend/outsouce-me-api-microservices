-- liquibase formatted sql

-- changeset oxygensend:1716385882394-1
CREATE INDEX createdAt_idx ON user (created_at DESC);

