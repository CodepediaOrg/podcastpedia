CREATE SCHEMA `keycloakdb` DEFAULT CHARACTER SET utf8 ;

-- add a playlist column to the subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `playlist` VARCHAR(45) NOT NULL DEFAULT 'default' AFTER `subscription_date`;

-- make the playlist column part of the primary key
ALTER TABLE `podcast_db`.`subscriptions`
CHANGE COLUMN `playlist` `playlist` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `playlist`);

-- fill subscriptions with default for existing subscriptions
update subscriptions
set playlist='default'
where playlist=''or playlist is null;

-- change column email to user_id in subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
CHANGE COLUMN `email` `user_id` VARCHAR(80) CHARACTER SET 'latin1' NOT NULL ;
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `email` VARCHAR(85) NULL AFTER `playlist`;


