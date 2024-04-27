-- liquibase formatted sql

-- changeset oxygensend:1714158192112-4
ALTER TABLE attachment
    ADD application_id BIGINT NULL;

-- changeset oxygensend:1714158192112-5
ALTER TABLE attachment MODIFY application_id BIGINT NOT NULL;

-- changeset oxygensend:1714158192112-6
ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES application (id);

-- changeset oxygensend:1714158192112-1
ALTER TABLE attachment DROP COLUMN size;

-- changeset oxygensend:1714158192112-2
ALTER TABLE attachment
    ADD size BIGINT NOT NULL;

-- changeset oxygensend:1714158192112-3
ALTER TABLE application MODIFY status INT NULL;

