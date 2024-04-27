-- liquibase formatted sql

-- changeset oxygensend:1713195441596-5
ALTER TABLE job_offer
    ADD CONSTRAINT uc_joboffer_slug UNIQUE (slug);

-- changeset oxygensend:1713195441596-1
ALTER TABLE job_offer DROP COLUMN form_of_employment;
ALTER TABLE job_offer DROP COLUMN work_types;

-- changeset oxygensend:1713195441596-2
ALTER TABLE job_offer
    ADD form_of_employment VARCHAR(255) NOT NULL;

-- changeset oxygensend:1713195441596-4
ALTER TABLE job_offer
    ADD work_types VARCHAR(255) NULL;

