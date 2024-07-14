-- liquibase formatted sql

-- changeset oxygensend:1720945917199-3
ALTER TABLE job_offer DROP FOREIGN KEY FK_JOBOFFER_ON_ADDRESS;

-- changeset oxygensend:1720945917199-4
ALTER TABLE job_offer DROP FOREIGN KEY FK_JOBOFFER_ON_USER;

-- changeset oxygensend:1720945917199-5
DROP TABLE job_offer;


