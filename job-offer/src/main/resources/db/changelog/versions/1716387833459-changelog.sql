-- liquibase formatted sql

-- changeset oxygensend:1716387833459-3
CREATE INDEX archived_createdAt_idx ON job_offer (archived, created_at DESC);

-- changeset oxygensend:1716387833459-4
CREATE INDEX archived_user_popularity_idx ON job_offer (archived, user_id, popularity_order DESC);

-- changeset oxygensend:1716387833459-5
CREATE INDEX valid_to_idx ON job_offer (valid_to);

-- changeset oxygensend:1716387833459-2
CREATE UNIQUE INDEX postCode_city_idx ON address (post_code, city);

