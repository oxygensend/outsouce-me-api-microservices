-- liquibase formatted sql

-- changeset oxygensend:1715888603127-5
DROP TABLE job_offer_applications;

-- changeset oxygensend:1715888603127-1
ALTER TABLE salary_range MODIFY currency VARCHAR (3) NOT NULL;

