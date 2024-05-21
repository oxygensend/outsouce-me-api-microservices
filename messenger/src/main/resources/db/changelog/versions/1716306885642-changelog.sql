-- liquibase formatted sql

-- changeset oxygensend:1716306885642-1
ALTER TABLE mail_message
    ADD created_at datetime NULL;

