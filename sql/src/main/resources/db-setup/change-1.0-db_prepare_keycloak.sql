CREATE SCHEMA `keycloakdb` DEFAULT CHARACTER SET utf8 ;

-- add a "category" column to the subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `category` VARCHAR(45) NOT NULL DEFAULT 'default' AFTER `subscription_date`;

-- make the category column part of the primary key
ALTER TABLE `podcast_db`.`subscriptions`
CHANGE COLUMN `category` `category` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `category`);

-- fill subscriptions with default for existing subscriptions
update subscriptions
set category='default'
where category=''or category is null;

-- change column email to user_id in subscriptions table
ALTER TABLE `podcast_db`.`subscriptions`
CHANGE COLUMN `email` `user_id` VARCHAR(80) CHARACTER SET 'latin1' NOT NULL ;
ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `email` VARCHAR(85) NULL AFTER `category`;

-- add passwd_reset_token
ALTER TABLE `podcast_db`.`users`
ADD COLUMN `password_reset_token` VARCHAR(45) NULL AFTER `user_id`;




