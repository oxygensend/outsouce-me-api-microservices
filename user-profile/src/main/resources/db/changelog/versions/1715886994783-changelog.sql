-- liquibase formatted sql
-- changeset oxygensend:1715886994783-1
ALTER TABLE user
    ADD CONSTRAINT uc_user_slug UNIQUE (slug);

-- changeset oxygensend:1715886994783-5
DROP TABLE attachment;

-- changeset oxygensend:1715886994783-6
DROP TABLE opinion;
