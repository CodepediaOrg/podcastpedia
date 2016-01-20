CREATE SCHEMA `keycloakdb` DEFAULT CHARACTER SET utf8 ;

ALTER TABLE `podcast_db`.`subscriptions` 
ADD COLUMN `playlist` VARCHAR(45) NOT NULL DEFAULT 'default' AFTER `playlist`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `playlist`);

ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `playlist` VARCHAR(45) NULL DEFAULT 'default' AFTER `subscription_date`;

ALTER TABLE `podcast_db`.`subscriptions`
CHANGE COLUMN `playlist` `playlist` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`email`, `podcast_id`, `playlist`);

-- fill subscriptions with default for existing subscriptions
update subscriptions
set playlist='default'
where playlist=''or playlist is null;
