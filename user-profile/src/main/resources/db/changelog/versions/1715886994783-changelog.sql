-- liquibase formatted sql

-- changeset oxygensend:1715886994783-2
ALTER TABLE opinion DROP FOREIGN KEY FK3wd438kn67pklydy9gt0prvj3;

-- changeset oxygensend:1715886994783-3
ALTER TABLE attachment DROP FOREIGN KEY FKf383t0luhfn2qv3bjsn2uca30;

-- changeset oxygensend:1715886994783-4
ALTER TABLE opinion DROP FOREIGN KEY FKqd3dfvau3vnjvmgvn40y9au3n;

-- changeset oxygensend:1715886994783-1
ALTER TABLE user
    ADD CONSTRAINT uc_user_slug UNIQUE (slug);

-- changeset oxygensend:1715886994783-5
DROP TABLE attachment;

-- changeset oxygensend:1715886994783-6
DROP TABLE opinion;

-- changeset oxygensend:1715886994783-7
DROP TABLE slug_history;
