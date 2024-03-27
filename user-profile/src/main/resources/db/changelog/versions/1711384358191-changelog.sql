-- liquibase formatted sql

-- changeset oxygensend:1711384358191-1
CREATE TABLE address
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    city      VARCHAR(255) NULL,
    post_code VARCHAR(255) NULL,
    lon       VARCHAR(255) NULL,
    lat       VARCHAR(255) NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-2
CREATE TABLE attachment
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    size          VARCHAR(255) NOT NULL,
    created_by_id BIGINT       NOT NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-3
CREATE TABLE company
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at datetime     NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-4
CREATE TABLE education
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    university_id  BIGINT NULL,
    individual_id  BIGINT NULL,
    field_of_study VARCHAR(255) NOT NULL,
    title          VARCHAR(255) NULL,
    `description`  VARCHAR(1000) NULL,
    grade DOUBLE NULL,
    start_date     date         NOT NULL,
    end_date       date NULL,
    updated_at     datetime NULL,
    created_at     datetime     NOT NULL,
    CONSTRAINT pk_education PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-5
CREATE TABLE job_position
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    form_of_employment VARCHAR(255) NOT NULL,
    individual_id      BIGINT NULL,
    name               VARCHAR(255) NOT NULL,
    `description`      VARCHAR(1000) NULL,
    active             BIT(1)       NOT NULL,
    company_id         BIGINT NULL,
    start_date         date         NOT NULL,
    end_date           date NULL,
    created_at         datetime     NOT NULL,
    updated_at         datetime NULL,
    CONSTRAINT pk_jobposition PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-6
CREATE TABLE language
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT NULL,
    name          VARCHAR(30) NOT NULL,
    `description` VARCHAR(1000) NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    CONSTRAINT pk_language PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-7
CREATE TABLE opinion
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    `description` VARCHAR(1000) NULL,
    scale         INT    NOT NULL,
    from_who_id   BIGINT NOT NULL,
    to_who_id     BIGINT NOT NULL,
    CONSTRAINT pk_opinion PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-8
CREATE TABLE university
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_university PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-9
CREATE TABLE user
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    email               VARCHAR(255) NOT NULL,
    name                VARCHAR(255) NOT NULL,
    surname             VARCHAR(255) NOT NULL,
    phone_number        VARCHAR(255) NULL,
    `description`       VARCHAR(1000) NULL,
    github_url          VARCHAR(255) NULL,
    linkedin_url        VARCHAR(255) NULL,
    date_of_birth       date NULL,
    redirect_count      INT          NOT NULL,
    account_type        SMALLINT NULL,
    slug                VARCHAR(255) NOT NULL,
    looking_for_job     BIT(1)       NOT NULL,
    active_job_position VARCHAR(255) NULL,
    opinions_rate DOUBLE NOT NULL,
    experience          SMALLINT NULL,
    popularity_order DOUBLE NOT NULL,
    image_name          VARCHAR(255) NULL,
    image_name_small    VARCHAR(255) NULL,
    created_at          datetime NULL,
    updated_at          datetime NULL,
    technologies        VARCHAR(255) NULL,
    address_id          BIGINT       NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset oxygensend:1711384358191-10
ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_CREATED_BY FOREIGN KEY (created_by_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-11
ALTER TABLE education
    ADD CONSTRAINT FK_EDUCATION_ON_INDIVIDUAL FOREIGN KEY (individual_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-12
ALTER TABLE education
    ADD CONSTRAINT FK_EDUCATION_ON_UNIVERSITY FOREIGN KEY (university_id) REFERENCES university (id);

-- changeset oxygensend:1711384358191-13
ALTER TABLE job_position
    ADD CONSTRAINT FK_JOBPOSITION_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

-- changeset oxygensend:1711384358191-14
ALTER TABLE job_position
    ADD CONSTRAINT FK_JOBPOSITION_ON_INDIVIDUAL FOREIGN KEY (individual_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-15
ALTER TABLE language
    ADD CONSTRAINT FK_LANGUAGE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-16
ALTER TABLE opinion
    ADD CONSTRAINT FK_OPINION_ON_FROM_WHO FOREIGN KEY (from_who_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-17
ALTER TABLE opinion
    ADD CONSTRAINT FK_OPINION_ON_TO_WHO FOREIGN KEY (to_who_id) REFERENCES user (id);

-- changeset oxygensend:1711384358191-18
ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

