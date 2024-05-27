-- liquibase formatted sql

-- changeset oxygensend:1716718309238-6
ALTER TABLE job_offer
    ADD address_id BIGINT NULL;

-- changeset oxygensend:1716718309238-7
ALTER TABLE user
    ADD opinions_count INT NULL;

-- changeset oxygensend:1716718309238-8
ALTER TABLE user MODIFY opinions_count INT NOT NULL;

-- changeset oxygensend:1716718309238-9
ALTER TABLE job_offer
    ADD CONSTRAINT FK_JOBOFFER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

-- changeset oxygensend:1716718309238-1
ALTER TABLE user MODIFY address_id BIGINT NULL;

-- changeset oxygensend:1716718309238-2
ALTER TABLE job_position DROP COLUMN form_of_employment;

-- changeset oxygensend:1716718309238-3
ALTER TABLE job_position
    ADD form_of_employment SMALLINT NOT NULL;


