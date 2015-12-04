CREATE SCHEMA `keycloakdb` DEFAULT CHARACTER SET utf8 ;

ALTER TABLE `podcast_db`.`subscriptions`
ADD COLUMN `playlist` VARCHAR(45) NULL DEFAULT 'default' AFTER `subscription_date`;

