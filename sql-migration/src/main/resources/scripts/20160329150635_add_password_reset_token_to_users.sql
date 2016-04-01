-- // add password reset token to users
-- Migration SQL that makes the change goes here.

-- add passwd_reset_token
ALTER TABLE `podcast_db`.`users`
ADD COLUMN `password_reset_token` VARCHAR(45) NULL AFTER `registration_token`;

-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE `podcast_db`.`users`
DROP COLUMN `password_reset_token`;


