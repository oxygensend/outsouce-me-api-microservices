-- liquibase formatted sql

-- changeset oxygensend:1716385440253-3
CREATE INDEX accountType_lookingForJob_idx ON user (account_type, looking_for_job);

-- changeset oxygensend:1716385440253-4
CREATE INDEX accountType_lookingForJob_newest_idx ON user (account_type, looking_for_job, created_at DESC);

-- changeset oxygensend:1716385440253-5
CREATE INDEX accountType_lookingForJob_popular_idx ON user (account_type, looking_for_job, popularity_order DESC);


