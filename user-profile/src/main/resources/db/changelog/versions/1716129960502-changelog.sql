-- liquibase formatted sql

-- changeset oxygensend:1716129960502-1
CREATE TABLE job_offer
(
    id           BIGINT NOT NULL,
    user_id      BIGINT NULL,
    experience   SMALLINT NULL,
    technologies VARCHAR(255) NULL,
    CONSTRAINT pk_joboffer PRIMARY KEY (id)
);

-- changeset oxygensend:1716129960502-2
ALTER TABLE job_offer
    ADD CONSTRAINT FK_JOBOFFER_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

