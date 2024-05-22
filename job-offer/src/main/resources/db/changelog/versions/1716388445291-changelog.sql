-- liquibase formatted sql

-- changeset oxygensend:1716388445291-3
CREATE INDEX createdAt_idx ON job_offer (created_at DESC);

-- changeset oxygensend:1716388445291-4
CREATE INDEX popularity_idx ON job_offer (popularity_order DESC);
