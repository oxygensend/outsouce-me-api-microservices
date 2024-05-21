-- liquibase formatted sql

-- changeset oxygensend:1716304576245-1
CREATE TABLE mail_message
(
    id           BIGINT        NOT NULL,
    subject      VARCHAR(255)  NOT NULL,
    content      VARCHAR(2048) NOT NULL,
    recipient_id VARCHAR(255) NULL,
    sender_id    VARCHAR(255) NULL,
    CONSTRAINT pk_mailmessage PRIMARY KEY (id)
);

-- changeset oxygensend:1716304576245-2
CREATE TABLE user
(
    id      VARCHAR(255) NOT NULL,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset oxygensend:1716304576245-3
ALTER TABLE mail_message
    ADD CONSTRAINT FK_MAILMESSAGE_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES user (id);

-- changeset oxygensend:1716304576245-4
ALTER TABLE mail_message
    ADD CONSTRAINT FK_MAILMESSAGE_ON_SENDER FOREIGN KEY (sender_id) REFERENCES user (id);

