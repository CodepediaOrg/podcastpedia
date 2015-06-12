-- delete the pcmDB database if existent
DROP DATABASE IF EXISTS pcmDB;

-- create the pcmDB database
CREATE DATABASE pcmDB; 

-- drop 'pcm' user
DROP USER 'pcm'@'localhost';

-- create the development user 'pcm' identified by the password 'pcm_pw'
CREATE USER 'pcm'@'localhost' IDENTIFIED BY 'pcm_pw';

-- verify user creation
select host, user, password from mysql.user;

-- verify user privileges
SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv, password FROM mysql.user WHERE user='pcm';
-- should have no privileges

-- grant full privileges to the user, for easy development
GRANT ALL PRIVILEGES ON *.* TO 'pcm'@'localhost';

-- verify privileges set
SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv, password FROM mysql.user WHERE user='pcm';