------------------------------------------------------------------------
-- MyBatis Migrations - script
------------------------------------------------------------------------
-- 20160329144627_create_changelog.sql
--  Create Changelog

-- Default DDL for changelog table that will keep
-- a record of the migrations that have been run.

-- You can modify this to suit your database before
-- running your first migration.

-- Be sure that ID and DESCRIPTION fields exist in
-- BigInteger and String compatible fields respectively.

CREATE TABLE CHANGELOG (
ID NUMERIC(20,0) NOT NULL,
APPLIED_AT VARCHAR(25) NOT NULL,
DESCRIPTION VARCHAR(255) NOT NULL
);

ALTER TABLE CHANGELOG
ADD CONSTRAINT PK_CHANGELOG
PRIMARY KEY (id);


INSERT INTO CHANGELOG (ID, APPLIED_AT, DESCRIPTION) VALUES (20160329144627, '2016-03-30 12:42:59', 'create changelog');

-- 20160329144628_first_migration.sql
--  First migration.
-- Migration SQL that makes the change goes here.

-- add a "category" column to the subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `category` VARCHAR(45) NOT NULL AFTER `subscription_date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `category`);

UPDATE `podcast_db`.`subscriptions`
SET category='default'
where category='';


INSERT INTO CHANGELOG (ID, APPLIED_AT, DESCRIPTION) VALUES (20160329144628, '2016-03-30 12:42:59', 'first migration');

-- 20160329150635_add_password_reset_token_to_users.sql
--  add password reset token to users
-- Migration SQL that makes the change goes here.

-- add passwd_reset_token
ALTER TABLE `podcast_db`.`users`
ADD COLUMN `password_reset_token` VARCHAR(45) NULL AFTER `registration_token`;


INSERT INTO CHANGELOG (ID, APPLIED_AT, DESCRIPTION) VALUES (20160329150635, '2016-03-30 12:42:59', 'add password reset token to users');

------------------------------------------------------------------------
-- MyBatis Migrations SUCCESS
-- Total time: 0s
-- Finished at: Wed Mar 30 12:42:59 CEST 2016
-- Final Memory: 7M/479M
------------------------------------------------------------------------
