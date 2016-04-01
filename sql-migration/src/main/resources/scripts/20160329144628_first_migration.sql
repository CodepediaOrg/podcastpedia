-- // First migration.
-- Migration SQL that makes the change goes here.

-- add a "category" column to the subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `category` VARCHAR(45) NOT NULL AFTER `subscription_date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `category`);

UPDATE `podcast_db`.`subscriptions`
SET category='default'
where category='';

-- //@UNDO
-- SQL to undo the change goes here.

-- this won't work, if the a user adds same podcast to several categories is not undoable anymore, as category becomes part of the primary key
-- one would have to add subscription_date to the primary key instead...
ALTER TABLE `podcast_db`.`subscriptions`
DROP COLUMN `category`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`);


