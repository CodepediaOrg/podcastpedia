------------------------------------------------------------------------
-- MyBatis Migrations - script
------------------------------------------------------------------------
-- 20160329150635_add_password_reset_token_to_users.sql
-- @UNDO
-- SQL to undo the change goes here.
ALTER TABLE `podcast_db`.`users`
DROP COLUMN `password_reset_token`;



DELETE FROM CHANGELOG WHERE ID = 20160329150635;

-- 20160329144628_first_migration.sql
-- @UNDO
-- SQL to undo the change goes here.

-- this won't work, if the a user adds same podcast to several categories is not undoable anymore, as category becomes part of the primary key
-- one would have to add subscription_date to the primary key instead...
ALTER TABLE `podcast_db`.`subscriptions`
DROP COLUMN `category`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`);



DELETE FROM CHANGELOG WHERE ID = 20160329144628;

-- 20160329144627_create_changelog.sql
-- @UNDO

DROP TABLE CHANGELOG;

DELETE FROM CHANGELOG WHERE ID = 20160329144627;

------------------------------------------------------------------------
-- MyBatis Migrations SUCCESS
-- Total time: 0s
-- Finished at: Wed Mar 30 12:44:11 CEST 2016
-- Final Memory: 7M/479M
------------------------------------------------------------------------
