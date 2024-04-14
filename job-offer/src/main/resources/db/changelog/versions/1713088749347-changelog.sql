-- liquibase formatted sql

-- changeset oxygensend:1713088749347-1
CREATE TABLE address
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    city      VARCHAR(255) NULL,
    post_code VARCHAR(255) NULL,
    lon       VARCHAR(255) NULL,
    lat       VARCHAR(255) NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-2
CREATE TABLE application
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       VARCHAR(255) NULL,
    job_offer_id  BIGINT NULL,
    status        INT    NOT NULL,
    `description` VARCHAR(255) NULL,
    deleted       BIT(1) NOT NULL,
    updated_at    datetime NULL,
    created_at    datetime NULL,
    CONSTRAINT pk_application PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-3
CREATE TABLE attachment
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    size          VARCHAR(255) NOT NULL,
    created_by_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-4
CREATE TABLE job_offer
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    name                   VARCHAR(255)  NOT NULL,
    `description`          VARCHAR(1028) NOT NULL,
    address_id             BIGINT NULL,
    user_id                VARCHAR(255) NULL,
    form_of_employment     SMALLINT      NOT NULL,
    salary_range_id        BIGINT NULL,
    experience             SMALLINT NULL,
    slug                   VARCHAR(255)  NOT NULL,
    number_of_applications INT           NOT NULL,
    redirect_count         INT           NOT NULL,
    popularity_order       INT NULL,
    display_order          INT NULL,
    archived               BIT(1)        NOT NULL,
    technologies           VARCHAR(255) NULL,
    work_types             SMALLINT NULL,
    valid_to               datetime NULL,
    updated_at             datetime NULL,
    created_at             datetime NULL,
    CONSTRAINT pk_joboffer PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-5
CREATE TABLE job_offer_applications
(
    job_offer_id    BIGINT NOT NULL,
    applications_id BIGINT NOT NULL
);

-- changeset oxygensend:1713088749347-6
CREATE TABLE salary_range
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    down_range DOUBLE NOT NULL,
    up_range DOUBLE NULL,
    currency VARCHAR(255) NOT NULL,
    type     SMALLINT NULL,
    CONSTRAINT pk_salaryrange PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-7
CREATE TABLE user
(
    id      VARCHAR(255) NOT NULL,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset oxygensend:1713088749347-8
ALTER TABLE job_offer_applications
    ADD CONSTRAINT uc_job_offer_applications_applications UNIQUE (applications_id);

-- changeset oxygensend:1713088749347-9
ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_JOB_OFFER FOREIGN KEY (job_offer_id) REFERENCES job_offer (id);

-- changeset oxygensend:1713088749347-10
ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

-- changeset oxygensend:1713088749347-11
ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_CREATED_BY FOREIGN KEY (created_by_id) REFERENCES user (id);

-- changeset oxygensend:1713088749347-12
ALTER TABLE job_offer
    ADD CONSTRAINT FK_JOBOFFER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

-- changeset oxygensend:1713088749347-13
ALTER TABLE job_offer
    ADD CONSTRAINT FK_JOBOFFER_ON_SALARY_RANGE FOREIGN KEY (salary_range_id) REFERENCES salary_range (id);

-- changeset oxygensend:1713088749347-14
ALTER TABLE job_offer
    ADD CONSTRAINT FK_JOBOFFER_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

-- changeset oxygensend:1713088749347-15
ALTER TABLE job_offer_applications
    ADD CONSTRAINT fk_joboffapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);

-- changeset oxygensend:1713088749347-16
ALTER TABLE job_offer_applications
    ADD CONSTRAINT fk_joboffapp_on_job_offer FOREIGN KEY (job_offer_id) REFERENCES job_offer (id);

