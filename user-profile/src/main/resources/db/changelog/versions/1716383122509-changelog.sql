-- liquibase formatted sql

-- changeset oxygensend:1716383122509-3
CREATE UNIQUE INDEX company_name_idx ON company (name);


-- changeset oxygensend:1716383122509-5
CREATE INDEX email_idx ON user (email);

-- changeset oxygensend:1716383122509-6
CREATE INDEX id_individualId_idx ON education (id, individual_id);

-- changeset oxygensend:1716383122509-7
CREATE INDEX id_individualId_idx ON job_position (id, individual_id);

-- changeset oxygensend:1716383122509-8
CREATE INDEX id_userId_idx ON language (id, user_id);

-- changeset oxygensend:1716383122509-9
CREATE INDEX individualId_startDate_idx ON education (individual_id, start_date DESC);

-- changeset oxygensend:1716383122509-10
CREATE INDEX individualId_startDate_idx ON job_position (individual_id, start_date DESC);

-- changeset oxygensend:1716383122509-11
CREATE INDEX name_idx ON university (name);

-- changeset oxygensend:1716383122509-12
CREATE INDEX opinionsRate_idx ON user (opinions_rate DESC);

-- changeset oxygensend:1716383122509-13
CREATE INDEX popularityOrder_idx ON user (popularity_order DESC);

-- changeset oxygensend:1716383122509-14
CREATE UNIQUE INDEX postCode_city_idx ON address (post_code, city);

-- changeset oxygensend:1716383122509-15
CREATE INDEX userId_createdAt_id ON language (user_id, created_at DESC);

-- changeset oxygensend:1716383122509-2
CREATE INDEX slug_idx ON user (slug);

