-- liquibase formatted sql

-- changeset oxygensend:1716100574123-1
CREATE INDEX archived_idx ON job_offer (archived);

-- changeset oxygensend:1716100574123-2
CREATE INDEX deleted_user_job_offer_created_at_idx ON application (deleted, user_id, job_offer_id, created_at);

-- changeset oxygensend:1716100574123-3
CREATE INDEX deleted_user_job_offer_status_idx ON application (deleted, user_id, job_offer_id, status);

-- changeset oxygensend:1716100574123-4
CREATE INDEX job_offer_user_idx ON application (job_offer_id, user_id);

-- changeset oxygensend:1716100574123-5
CREATE INDEX post_code_city_idx ON address (post_code, city);


