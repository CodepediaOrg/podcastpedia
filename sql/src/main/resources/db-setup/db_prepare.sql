-- delete the podcast_db database if existent
DROP DATABASE IF EXISTS podcast_db;

-- create the podcast_db database
CREATE DATABASE podcast_db;

-- drop 'podcast' user
--GRANT USAGE ON *.* TO 'podcast'@'localhost';
--DROP USER 'podcast'@'localhost';

-- create the development user 'pcm' identified by the password 'pcm_pw'
CREATE USER 'podcast'@'localhost' IDENTIFIED BY 'podcast';

-- verify user creation
select host, user from mysql.user;

-- verify user privileges
SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv FROM mysql.user WHERE user='podcast';
-- should have no privilegesuser

-- grant full privileges to the user, for easy development
GRANT ALL PRIVILEGES ON podcast_db.* TO 'podcast'@'localhost';

-- verify privileges set
SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv FROM mysql.user WHERE user='podcast';
